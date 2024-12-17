package de.schulung.spring.accounts.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
@AutoConfigureTestDatabase
class CustomersServiceLoggingOnCreationTests {

  @Autowired
  CustomersService customersService;

  @Test
  void shouldLogOnCustomerCreated(CapturedOutput output) {
    var logLength = output.length();
    var customer = Customer
      .builder()
      .name("Tom Mayer")
      .birthDate(LocalDate.of(1985, Month.JULY, 17))
      .build();

    customersService.createCustomer(customer);

    var newLog = output.subSequence(logLength, output.length() - 1);
    assertThat(newLog).contains("Kunde wurde angelegt");

  }

}
