package de.schulung.spring.accounts.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
// @Profile("dev")
@ConditionalOnProperty(
  name = "customers.initialization.enabled",
  havingValue = "true"
)
public class CustomersInitializer {

  private final CustomersService customersService;

  @EventListener(ContextRefreshedEvent.class)
  public void init() {
    if (customersService.count() < 1) {
      customersService.createCustomer(
        Customer
          .builder()
          .name("Test Customer")
          .birthDate(LocalDate.now().minusYears(20))
          .build()
      );
    }
  }
}
