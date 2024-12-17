package de.schulung.spring.accounts.domain.events;

import de.schulung.spring.accounts.domain.Customer;

public record CustomerUpdatedEvent(
  Customer customer
) {
}
