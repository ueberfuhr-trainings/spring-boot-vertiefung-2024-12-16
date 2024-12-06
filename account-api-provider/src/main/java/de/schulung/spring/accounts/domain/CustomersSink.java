package de.schulung.spring.accounts.domain;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface CustomersSink {

  Stream<Customer> findCustomers();

  default Stream<Customer> findCustomers(CustomerState state) {
    return findCustomers()
      .filter(customer -> customer.getState() == state);
  }

  Optional<Customer> findCustomer(UUID id);

  void createCustomer(Customer customer);

  long count();

  boolean existsCustomer(UUID id);

  void deleteCustomer(UUID id);

  void updateCustomer(Customer customer);

}
