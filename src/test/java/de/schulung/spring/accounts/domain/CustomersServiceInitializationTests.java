package de.schulung.spring.accounts.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
  properties = {
    "customers.initialization.enabled=true"
  }
)
//@ActiveProfiles("dev")
class CustomersServiceInitializationTests {

  @Autowired
  CustomersService customersService;

  @Test
  void shouldContainAtLeastOneCustomer() {

    assertThat(customersService.findCustomers())
      .isNotEmpty();

  }


}
