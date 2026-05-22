package de.schulung.quarkus.testing;

import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.text.MessageFormat;
import java.util.function.Supplier;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * JUnit Extension for capturing console output.
 * (Spring Boot has this extension built-in)
 */
public class OutputCaptureExtension
  implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

  private Handler handler;
  private CapturedOutput capturedOutput;

  @Override
  public void beforeEach(@NonNull ExtensionContext context) {

    final StringBuilder sb = new StringBuilder();
    this.handler = new Handler() {

      @Override
      public void publish(LogRecord record) {
        final var message = MessageFormat
          .format(
            record.getMessage(),
            record.getParameters()
          );
        sb.append(message);
      }

      @Override
      public void flush() {
      }

      @Override
      public void close() throws SecurityException {
      }
    };
    this.capturedOutput = new CapturedOutput(sb::toString);

    Logger
      .getLogger("")
      .addHandler(this.handler);
  }

  @Override
  public void afterEach(@NonNull ExtensionContext context) {
    Logger
      .getLogger("")
      .removeHandler(this.handler);
    this.handler = null;
    this.capturedOutput = null;
  }

  @Override
  public boolean supportsParameter(
    ParameterContext parameterContext,
    @NonNull ExtensionContext context
  ) {
    return parameterContext
      .getParameter()
      .getType()
      .equals(CapturedOutput.class);
  }

  @Override
  public Object resolveParameter(
    @NonNull ParameterContext parameterContext,
    @NonNull ExtensionContext context) {
    return this.capturedOutput;
  }

  @SuppressWarnings("NullableProblems")
  public record CapturedOutput(Supplier<String> output)
    implements CharSequence {

    @Override
    public int length() {
      return output.get().length();
    }

    @Override
    public char charAt(int index) {
      return output.get().charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
      return output.get().subSequence(start, end);
    }

    @Override
    public String toString() {
      return output.get();
    }
  }
}
