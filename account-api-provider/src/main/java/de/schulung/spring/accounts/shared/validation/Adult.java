package de.schulung.spring.accounts.shared.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.temporal.ChronoUnit;

@Target({
  ElementType.FIELD,
  ElementType.METHOD,
  ElementType.ANNOTATION_TYPE,
  ElementType.CONSTRUCTOR,
  ElementType.PARAMETER,
  ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = AdultValidator.class)
public @interface Adult {

  long value() default 18;

  ChronoUnit unit() default ChronoUnit.YEARS;

  String message() default "Must be an adult.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
