package de.schulung.quarkus;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.temporal.ChronoUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target({
  ElementType.FIELD,
  ElementType.PARAMETER,
  ElementType.METHOD,
  ElementType.ANNOTATION_TYPE,
  ElementType.CONSTRUCTOR,
  ElementType.TYPE_USE
})
@Documented
@Constraint(validatedBy = AdultValidator.class)
public @interface Adult {

  String message() default "Is not an adult.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  long value() default 18;

  ChronoUnit unit() default ChronoUnit.YEARS;

}
