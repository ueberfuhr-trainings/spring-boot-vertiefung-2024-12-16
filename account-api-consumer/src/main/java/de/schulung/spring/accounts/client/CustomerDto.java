package de.schulung.spring.accounts.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
  private LocalDate birthDate;
  @Pattern(regexp = "active|locked|disabled")
  private String state;

}
