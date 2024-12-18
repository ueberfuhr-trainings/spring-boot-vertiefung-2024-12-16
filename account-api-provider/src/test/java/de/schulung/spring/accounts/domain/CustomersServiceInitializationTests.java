package de.schulung.spring.accounts.domain;

import de.schulung.spring.accounts.test.DomainTestWithInitializationEnabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DomainTestWithInitializationEnabled
class CustomersServiceInitializationTests {

  @Autowired
  CustomersService customersService;

  @Test
  void shouldContainAtLeastOneCustomer() {

    assertThat(customersService.findCustomers())
      .isNotEmpty();

  }


}
