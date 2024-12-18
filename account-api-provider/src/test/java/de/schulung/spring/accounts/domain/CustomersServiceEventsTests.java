package de.schulung.spring.accounts.domain;

import de.schulung.spring.accounts.domain.events.CustomerCreatedEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.ApplicationEvents;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@DomainTest
public class CustomersServiceEventsTests {

  @Autowired
  CustomersService customersService;
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  ApplicationEvents applicationEvents;

  @Test
  void shouldPublishEventOnCreateCustomer() {
    var customer = Customer
      .builder()
      .name("Tom Mayer")
      .birthDate(LocalDate.of(1985, Month.JULY, 17))
      .build();

    customersService.createCustomer(customer);

    assertThat(applicationEvents.stream(CustomerCreatedEvent.class))
      .isNotEmpty()
      .first()
      .extracting(CustomerCreatedEvent::customer)
      .isSameAs(customer);
  }

}
