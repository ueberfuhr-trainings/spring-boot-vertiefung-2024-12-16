package de.schulung.spring.accounts.domain;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class CustomersSinkInMemoryImpl implements CustomersSink {

  private final Map<UUID, Customer> customers = new ConcurrentHashMap<>();

  @Override
  public Stream<Customer> findCustomers() {
    return customers
      .values()
      .stream();
  }

  @Override
  public Optional<Customer> findCustomer(UUID id) {
    return Optional.ofNullable(customers.get(id));
  }

  @Override
  public void createCustomer(Customer customer) {
    customer.setUuid(UUID.randomUUID());
    customers.put(customer.getUuid(), customer);
  }

  @Override
  public long count() {
    return customers.size();
  }

  @Override
  public boolean existsCustomer(UUID id) {
    return customers.containsKey(id);
  }

  @Override
  public void deleteCustomer(UUID id) {
    customers.remove(id);
  }

  @Override
  public void updateCustomer(Customer customer) {
    customers.put(customer.getUuid(), customer);
  }
}
