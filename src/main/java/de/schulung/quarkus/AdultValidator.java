package de.schulung.quarkus;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class AdultValidator
  implements ConstraintValidator<Adult, LocalDate> {

  private Adult constraintAnnotation;

  @Override
  public void initialize(Adult constraintAnnotation) {
    this.constraintAnnotation = constraintAnnotation;
  }

  @Override
  public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
    return null == value ||
      LocalDate
        .now()
        .minus(constraintAnnotation.value(), constraintAnnotation.unit())
        .plusDays(1)
        .isAfter(value);
  }

}
