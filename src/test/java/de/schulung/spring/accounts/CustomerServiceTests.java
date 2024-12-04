package de.schulung.spring.accounts;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class CustomerServiceTests {

  @Autowired
  CustomersService customersService;

  @Test
  void shouldFindPreviouslyCreatedCustomer() {
    Customer customer = new Customer();
    customer.setName("Tom Mayer");
    customer.setState("active");
    customer.setBirthDate(LocalDate.of(1985, Month.JULY, 3));

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
    Customer customer = new Customer();
    customer.setState("active");
    customer.setBirthDate(LocalDate.of(1985, Month.JULY, 3));

    assertThatThrownBy(() -> customersService.createCustomer(customer))
      .isInstanceOf(ValidationException.class);

  }


}
