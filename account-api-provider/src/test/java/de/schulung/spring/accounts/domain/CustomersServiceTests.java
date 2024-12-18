package de.schulung.spring.accounts.domain;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DomainTest
class CustomersServiceTests {

  @Autowired
  CustomersService customersService;

  @Test
  void testFindAll() {
    assertThat(customersService.findCustomers())
      .isNotNull();
  }

  @Test
  void shouldFindPreviouslyCreatedCustomer() {
    var customer = Customer
      .builder()
      .name("Tom Mayer")
      .state(CustomerState.ACTIVE)
      .birthDate(LocalDate.of(1985, Month.JULY, 3))
      .build();

    customersService.createCustomer(customer);

    assertThat(customer.getUuid()).isNotNull();

    var result = customersService.findCustomer(customer.getUuid());

    assertThat(result)
      .isPresent()
      .get()
      .usingRecursiveComparison()
      .isEqualTo(customer);

  }

  @Test
  void shouldThrowValidationExceptionOnCreateInvalidCustomer() {
    var customer = Customer
      .builder()
      .state(CustomerState.ACTIVE)
      .birthDate(LocalDate.of(1985, Month.JULY, 3))
      .build();

    assertThatThrownBy(() -> customersService.createCustomer(customer))
      .isInstanceOf(ValidationException.class);

  }

  @Test
  void shouldThrowValidationExceptionOnCreateNonAdultCustomer() {
    var customer = Customer
      .builder()
      .name("Tom Mayer")
      .state(CustomerState.ACTIVE)
      .birthDate(LocalDate.now().minusYears(10))
      .build();

    assertThatThrownBy(() -> customersService.createCustomer(customer))
      .isInstanceOf(ValidationException.class);

  }


}
