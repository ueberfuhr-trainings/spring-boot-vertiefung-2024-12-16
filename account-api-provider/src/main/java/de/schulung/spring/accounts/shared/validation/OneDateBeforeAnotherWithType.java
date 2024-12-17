package de.schulung.spring.accounts.shared.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;

/**
 * Use this constraint to validate two date fields against each other
 * with a given interface type. This is the best solution when
 * reflection is prohibited (e.g. sandbox, native images).
 * <p>
 * You can use this annotation like this:
 *
 * <pre>
 * public class Training {
 *
 *   private LocalDate startDate;
 *   private LocalDate endDate;
 *
 *   // Use Getter Naming here!
 *  {@literal @}OneDateBeforeAnotherWithType
 *   OneDateBeforeAnotherModel getOneDateBeforeAnotherModel() {
 *     return new OneDateBeforeAnotherModelImpl(startDate, endDate);
 *   }
 *
 *   // ...
 *
 * }
 * </pre>
 * </p>
 * <p>
 * Or you could also implement the interface directly:
 * <pre>
 * {@literal @}OneDateBeforeAnotherWithType
 *  public class Training implements OneDateBeforeAnotherModel {
 *
 *    private LocalDate startDate;
 *    private LocalDate endDate;
 *
 *   {@literal @}Override
 *    public LocalDate one() {
 *      return startDate;
 *    }
 *
 *   {@literal @}Override
 *    public LocalDate other() {
 *      return endDate;
 *    }
 *
 *    // ...
 *
 * }
 * </pre>
 * </p>
 */
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
@Constraint(validatedBy = {
  OneDateBeforeAnotherWithType.ValidatorImpl.class
})
public @interface OneDateBeforeAnotherWithType {

  String message() default "Must have one date before another.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  interface OneDateBeforeAnotherModel {
    LocalDate one();

    LocalDate other();
  }

  record OneDateBeforeAnotherModelImpl(
    LocalDate one,
    LocalDate other
  ) implements OneDateBeforeAnotherModel {
  }

  class ValidatorImpl
    implements ConstraintValidator<OneDateBeforeAnotherWithType, OneDateBeforeAnotherWithType.OneDateBeforeAnotherModel> {

    @Override
    public boolean isValid(OneDateBeforeAnotherModel value, ConstraintValidatorContext context) {
      return
        null == value
          || value.one().isBefore(value.other())
          || value.one().isEqual(value.other());
    }
  }

}
