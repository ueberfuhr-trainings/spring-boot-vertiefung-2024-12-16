package de.schulung.spring.accounts.domain;

import de.schulung.spring.accounts.shared.validation.Adult;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
public class Customer {

  private UUID uuid;
  @NotNull
  private String name;
  @NotNull
  @Adult
  private LocalDate birthDate;
  @Builder.Default
  private CustomerState state = CustomerState.ACTIVE;

}
