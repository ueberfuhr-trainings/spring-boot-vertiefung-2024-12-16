package de.schulung.spring.accounts.persistence;

import de.schulung.spring.accounts.domain.Customer;
import de.schulung.spring.accounts.domain.CustomerState;
import de.schulung.spring.accounts.domain.CustomersSink;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class CustomersSinkJpaImpl implements CustomersSink {

  private final CustomerEntityRepository repo;
  private final CustomerEntityMapper mapper;

  @Override
  public Stream<Customer> findCustomers() {
    return repo.findAll()
      .stream()
      .map(mapper::map);
  }

  @Override
  public Stream<Customer> findCustomers(CustomerState state) {
    return repo.findAllByState(state)
      .stream()
      .map(mapper::map);
  }

  @Override
  public Optional<Customer> findCustomer(UUID id) {
    return repo.findById(id)
      .map(mapper::map);
  }

  @Override
  public void createCustomer(Customer customer) {
    var entity = mapper.map(customer);
    repo.save(entity);
    //customer.setUuid(entity.getUuid());
    mapper.copy(entity, customer);
  }

  @Override
  public long count() {
    return repo.count();
  }

  @Override
  public boolean existsCustomer(UUID id) {
    return repo.existsById(id);
  }

  @Override
  public void deleteCustomer(UUID id) {
    repo.deleteById(id);
  }

  @Override
  public void updateCustomer(Customer customer) {
    var entity = mapper.map(customer);
    repo.save(entity);
    mapper.copy(entity, customer);
  }
}
