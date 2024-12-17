package de.schulung.spring.accounts.domain.events;

import de.schulung.spring.accounts.domain.Customer;

public record CustomerCreatedEvent(
  Customer customer
) {
}
