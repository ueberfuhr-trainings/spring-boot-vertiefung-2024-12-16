package de.schulung.spring.accounts.persistence;

import de.schulung.spring.accounts.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerEntityMapper {

  Customer map(CustomerEntity customerDto);

  CustomerEntity map(Customer customer);

  void copy(CustomerEntity source, @MappingTarget Customer target);

}
