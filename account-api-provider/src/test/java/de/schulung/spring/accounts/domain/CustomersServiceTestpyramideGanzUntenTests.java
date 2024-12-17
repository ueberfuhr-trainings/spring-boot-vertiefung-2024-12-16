package de.schulung.spring.accounts.domain;

import jakarta.validation.ValidationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

class CustomersServiceTestpyramideGanzUntenTests {

  final CustomersService customersService = new CustomersService(
    new CustomersSinkInMemoryImpl(),
    Mockito.mock(ApplicationEventPublisher.class)
  );

  @Test
  void shouldAssignUuidOnCreate() {
    var customer = Customer
      .builder()
      .name("Tom Mayer")
      .state(CustomerState.ACTIVE)
      .birthDate(LocalDate.of(1985, Month.JULY, 3))
      .build();

    customersService.createCustomer(customer);

    assertThat(customer.getUuid()).isNotNull();

  }

  @Disabled // does not work with Bean Validation
  @Test
  void shouldThrowValidationExceptionOnCreateInvalidCustomer() {
    var customer = Customer
      .builder()
      .state(CustomerState.ACTIVE)
      .birthDate(LocalDate.of(1985, Month.JULY, 3))
      .build();

    Assertions.assertThatThrownBy(() -> customersService.createCustomer(customer))
      .isInstanceOf(ValidationException.class);

  }

}
