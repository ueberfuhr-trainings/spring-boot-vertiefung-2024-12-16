package de.schulung.spring.accounts;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Validated
@Service
public class CustomersService {

  private final Map<UUID, Customer> customers = new ConcurrentHashMap<>();

  public Stream<Customer> findCustomers() {
    return customers
      .values()
      .stream();
  }

  public Optional<Customer> findCustomer(@NotNull UUID id) {
    return Optional.ofNullable(customers.get(id));
  }

  public void createCustomer(@Valid Customer customer) {
    customer.setUuid(UUID.randomUUID());
    customers.put(customer.getUuid(), customer);
  }

  public boolean deleteCustomer(UUID id) {
    return customers.remove(id) != null;
  }

  // boolean exists(id) ?


}
