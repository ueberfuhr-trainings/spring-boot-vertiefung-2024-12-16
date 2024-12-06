package de.schulung.spring.accounts.persistence;

import de.schulung.spring.accounts.domain.CustomerState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "Customer")
@Table(name = "CUSTOMERS")
public class CustomerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID uuid;
  @NotNull
  private String name;
  @NotNull
  @Column(name = "BIRTH_DATE")
  private LocalDate birthDate;
  private CustomerState state = CustomerState.ACTIVE;

}
