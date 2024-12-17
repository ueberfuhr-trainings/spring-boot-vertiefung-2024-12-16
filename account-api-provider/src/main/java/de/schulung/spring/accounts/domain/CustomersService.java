package de.schulung.spring.accounts.domain;

import de.schulung.spring.accounts.domain.events.CustomerCreatedEvent;
import de.schulung.spring.accounts.domain.events.CustomerDeletedEvent;
import de.schulung.spring.accounts.domain.events.CustomerUpdatedEvent;
import de.schulung.spring.accounts.shared.aspects.LogPerformance;
import de.schulung.spring.accounts.shared.aspects.PublishEvent;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Validated
@Service
@RequiredArgsConstructor
public class CustomersService {

  private final CustomersSink sink;

  public Stream<Customer> findCustomers() {
    return sink.findCustomers();
  }

  @LogPerformance
  public Stream<Customer> findCustomers(@NotNull CustomerState state) {
    return sink.findCustomers(state);
  }

  public Optional<Customer> findCustomer(@NotNull UUID id) {
    return sink.findCustomer(id);
  }

  @PublishEvent(CustomerCreatedEvent.class)
  public void createCustomer(@Valid Customer customer) {
    sink.createCustomer(customer);
  }

  // TODO if not deleted -> how to avoid publishing the event?
  // throw exception?
  // annotate method for interpreting boolean return value?
  @PublishEvent(CustomerDeletedEvent.class)
  public boolean deleteCustomer(@NotNull UUID id) {
    if (sink.existsCustomer(id)) {
      sink.deleteCustomer(id);
      return true;
    } else {
      return false;
    }
  }

  // TODO if not updated -> how to avoid publishing the event?
  // throw exception?
  // annotate method for interpreting boolean return value?
  @PublishEvent(CustomerUpdatedEvent.class)
  public boolean updateCustomer(@NotNull Customer customer) {
    if (sink.existsCustomer(customer.getUuid())) {
      sink.updateCustomer(customer);
      return true;
    } else {
      return false;
    }
  }

  public long count() {
    return sink.count();
  }

  // boolean exists(id) ?


}
