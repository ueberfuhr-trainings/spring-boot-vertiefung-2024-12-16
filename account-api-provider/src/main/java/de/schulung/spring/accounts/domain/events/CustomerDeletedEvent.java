package de.schulung.spring.accounts.domain.events;

import java.util.UUID;

public record CustomerDeletedEvent(
  UUID uuid
) {
}
