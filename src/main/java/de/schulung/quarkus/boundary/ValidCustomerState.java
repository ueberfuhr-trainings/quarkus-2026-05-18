package de.schulung.quarkus.boundary;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
@Constraint(validatedBy = {})
@Pattern(regexp = "active|locked|disabled")
@ReportAsSingleViolation
public @interface ValidCustomerState {

  String message() default "Not a valid customer state.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
