package de.schulung.spring.accounts.shared.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class AdultValidator
  implements ConstraintValidator<Adult, LocalDate> {

  private Adult adult;

  @Override
  public void initialize(Adult adult) {
    this.adult = adult;
  }

  @Override
  public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
    return null == value || LocalDate
      .now()
      .minus(adult.value(), adult.unit())
      .plusDays(1)
      .isAfter(value);
  }
}
