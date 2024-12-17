package de.schulung.spring.accounts.domain;

import de.schulung.spring.accounts.domain.events.CustomerCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomerEventsLogger {

  @EventListener
  public void logCustomerEvent(CustomerCreatedEvent event) {
    log.info("Kunde wurde angelegt");
  }

}
