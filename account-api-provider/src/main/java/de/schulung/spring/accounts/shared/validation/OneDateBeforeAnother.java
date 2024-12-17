package de.schulung.spring.accounts.shared.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.function.Function;

/**
 * Use this constraint to validate two date fields against each other.
 * This variant uses reflection, so it does not work in scenarios,
 * where reflection is prohibited (e.g. sandbox, native images).
 * <p>
 * You can use this annotation in separate ways, which is preferred in the
 * following order:
 *
 * <h1>Referencing field names:</h1>
 * <p>
 * You could annotate the class and name the fields within the class.
 *
 * <pre>
 * {@literal @}OneDateBeforeAnother(
 *    one = "startDate",
 *    other = "endDate"
 *  )
 *  public class Training {
 *
 *    private LocalDate startDate;
 *    private LocalDate endDate;
 *
 *    // ...
 *
 *  }
 * </pre>
 * </p>
 *
 * <h1>Annotating fields:</h1>
 * <p>
 * You could annotate the fields within the class.
 *
 * <pre>
 * {@literal @}OneDateBeforeAnother
 *  public class Training {
 *
 *   {@literal @}OneDateBeforeAnother.One
 *    private LocalDate startDate;
 *   {@literal @}OneDateBeforeAnother.Other
 *    private LocalDate endDate;
 *
 *    // ...
 *
 *  }
 * </pre>
 * </p>
 */
@Target({
  ElementType.METHOD,
  ElementType.TYPE,
  ElementType.ANNOTATION_TYPE,
  ElementType.CONSTRUCTOR,
  ElementType.PARAMETER,
  ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {
  OneDateBeforeAnother.ValidatorImpl.class
})
public @interface OneDateBeforeAnother {

  String one() default "";

  String other() default "";

  String message() default "Must have one date before another.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  @Target(ElementType.FIELD)
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @interface One {
  }

  @Target(ElementType.FIELD)
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @interface Other {
  }

  class ValidatorImpl
    implements ConstraintValidator<OneDateBeforeAnother, Object> {

    private OneDateBeforeAnother odba;

    private static <T> T failWithInvalidState() {
      throw new IllegalStateException("Invalid use of @OneDateBeforeAnother.");
    }

    @SneakyThrows
    private static Field findField(Class<?> objectClass, String fieldName) {
      return objectClass
        .getDeclaredField(fieldName);
    }

    @SneakyThrows
    private static Field findField(Class<?> objectClass, Class<? extends Annotation> annotation) {
      return Arrays.stream(objectClass.getDeclaredFields())
        .filter(f -> f.isAnnotationPresent(annotation))
        .findFirst() // TODO how to deal multiple annotated fields?
        .orElseGet(ValidatorImpl::failWithInvalidState);
    }

    @SneakyThrows
    private static LocalDate readLocalDateFromField(Object object, Function<Class<?>, Field> fieldFinder) {
      var field = fieldFinder.apply(object.getClass());
      field.setAccessible(true);
      var value = field.get(object);
      return value instanceof LocalDate result
        ? result
        : failWithInvalidState();
    }

    @Override
    public void initialize(OneDateBeforeAnother odba) {
      this.odba = odba;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
      final var oneDate = readLocalDateFromField(
        value,
        !odba.one().isEmpty()
          ? c -> findField(c, odba.one())
          : c -> findField(c, One.class)
      );
      final var otherDate = readLocalDateFromField(
        value,
        !odba.other().isEmpty()
          ? c -> findField(c, odba.other())
          : c -> findField(c, Other.class)
      );
      return
        oneDate.isBefore(otherDate)
          || oneDate.isEqual(otherDate);
    }
  }


}
