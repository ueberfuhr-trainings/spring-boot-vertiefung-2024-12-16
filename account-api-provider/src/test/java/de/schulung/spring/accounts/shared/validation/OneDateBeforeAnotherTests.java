package de.schulung.spring.accounts.shared.validation;

import de.schulung.spring.accounts.shared.validation.OneDateBeforeAnotherWithType.OneDateBeforeAnotherModel;
import de.schulung.spring.accounts.shared.validation.OneDateBeforeAnotherWithType.OneDateBeforeAnotherModelImpl;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OneDateBeforeAnotherTests {

  @Autowired
  Validator validator;

  // ---------------------------------------------

  @Getter
  @AllArgsConstructor
  @OneDateBeforeAnother(
    one = "startDate",
    other = "endDate"
  )
  static class TrainingWithFieldNames {

    private LocalDate startDate;
    private LocalDate endDate;

  }

  @Test
  void shouldValidateForFieldNamesInvalid() {
    var objToValidate = new TrainingWithFieldNames(
      LocalDate.now(),
      LocalDate.now().minusDays(1)
    );
    var violations = validator.validate(objToValidate);
    assertThat(violations).isNotEmpty();
  }

  @Test
  void shouldValidateForFieldNamesValid() {
    var objToValidate = new TrainingWithFieldNames(
      LocalDate.now().minusDays(1),
      LocalDate.now()
    );
    var violations = validator.validate(objToValidate);
    assertThat(violations).isEmpty();
  }

  // ---------------------------------------------

  @Getter
  @AllArgsConstructor
  @OneDateBeforeAnother
  static class TrainingWithFieldAnnotations {

    @OneDateBeforeAnother.One
    private LocalDate startDate;
    @OneDateBeforeAnother.Other
    private LocalDate endDate;

  }

  @Test
  void shouldValidateForFieldAnnotationsInvalid() {
    var objToValidate = new TrainingWithFieldAnnotations(
      LocalDate.now(),
      LocalDate.now().minusDays(1)
    );
    var violations = validator.validate(objToValidate);
    assertThat(violations).isNotEmpty();
  }

  @Test
  void shouldValidateForFieldAnnotationsValid() {
    var objToValidate = new TrainingWithFieldAnnotations(
      LocalDate.now().minusDays(1),
      LocalDate.now()
    );
    var violations = validator.validate(objToValidate);
    assertThat(violations).isEmpty();
  }


  // ---------------------------------------------

  @Getter
  @AllArgsConstructor
  static class TrainingWithOneDateBeforeAnotherModel {

    private LocalDate startDate;
    private LocalDate endDate;

    @OneDateBeforeAnotherWithType
    public OneDateBeforeAnotherModel getOneDateBeforeAnotherModel() {
      return new OneDateBeforeAnotherModelImpl(startDate, endDate);
    }

  }

  @Test
  void shouldValidateForOneDateBeforeAnotherModelInvalid() {
    var objToValidate = new TrainingWithOneDateBeforeAnotherModel(
      LocalDate.now(),
      LocalDate.now().minusDays(1)
    );
    var violations = validator.validate(objToValidate);
    assertThat(violations).isNotEmpty();
  }

  @Test
  void shouldValidateForOneDateBeforeAnotherModelValid() {
    var objToValidate = new TrainingWithOneDateBeforeAnotherModel(
      LocalDate.now().minusDays(1),
      LocalDate.now()
    );
    var violations = validator.validate(objToValidate);
    assertThat(violations).isEmpty();
  }

  // ---------------------------------------------

  @Getter
  @AllArgsConstructor
  @OneDateBeforeAnotherWithType
  static class TrainingWithOneDateBeforeAnotherModel2
    implements OneDateBeforeAnotherModel {

    private LocalDate startDate;
    private LocalDate endDate;

    @Override
    public LocalDate one() {
      return startDate;
    }

    @Override
    public LocalDate other() {
      return endDate;
    }
  }

  @Test
  void shouldValidateForOneDateBeforeAnotherModel2Invalid() {
    var objToValidate = new TrainingWithOneDateBeforeAnotherModel2(
      LocalDate.now(),
      LocalDate.now().minusDays(1)
    );
    var violations = validator.validate(objToValidate);
    assertThat(violations).isNotEmpty();
  }

  @Test
  void shouldValidateForOneDateBeforeAnotherModel2Valid() {
    var objToValidate = new TrainingWithOneDateBeforeAnotherModel2(
      LocalDate.now().minusDays(1),
      LocalDate.now()
    );
    var violations = validator.validate(objToValidate);
    assertThat(violations).isEmpty();
  }


}
