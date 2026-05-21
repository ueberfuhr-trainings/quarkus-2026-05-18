package de.schulung.java;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate a method for invocation by SayHelloFramework.
 * The method must not have parameters, but must provide a return value.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
// @Inherited
// @Documented
public @interface SayHello {
  /**
   * Prefix for the message.
   */
  String value() default "Hallo!";
}
