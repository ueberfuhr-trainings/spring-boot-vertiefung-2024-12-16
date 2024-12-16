package de.schulung.spring.accounts.boundary;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.schulung.spring.accounts.boundary.validation.CustomerStatePattern;
import de.schulung.spring.accounts.shared.validation.Adult;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class CustomerDto {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private UUID uuid;
  @NotNull
  private String name;
  @JsonProperty("birthdate")
  @NotNull
  @Adult
  private LocalDate birthDate;
  @CustomerStatePattern
  private String state;


}
