package de.schulung.spring.accounts;

import jakarta.validation.ValidationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerServiceTestpyramideGanzUntenTests {

  final CustomersService customersService = new CustomersService();

  @Test
  void shouldAssignUuidOnCreate() {
    Customer customer = new Customer();
    customer.setName("Tom Mayer");
    customer.setState("active");
    customer.setBirthDate(LocalDate.of(1985, Month.JULY, 3));

    customersService.createCustomer(customer);

    assertThat(customer.getUuid()).isNotNull();

  }

  @Disabled // does not work with Bean Validation
  @Test
  void shouldThrowValidationExceptionOnCreateInvalidCustomer() {
    Customer customer = new Customer();
    customer.setState("active");
    customer.setBirthDate(LocalDate.of(1985, Month.JULY, 3));

    Assertions.assertThatThrownBy(() -> customersService.createCustomer(customer))
      .isInstanceOf(ValidationException.class);

  }

}
