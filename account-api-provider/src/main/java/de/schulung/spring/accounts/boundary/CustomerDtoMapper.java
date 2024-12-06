package de.schulung.spring.accounts.boundary;

import de.schulung.spring.accounts.domain.Customer;
import de.schulung.spring.accounts.domain.CustomerState;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerDtoMapper {

  Customer map(CustomerDto customerDto);

  CustomerDto map(Customer customer);

  default String mapState(CustomerState state) {
    return null == state ? null : switch (state) {
      case ACTIVE -> "active";
      case LOCKED -> "locked";
      case DISABLED -> "disabled";
    };
  }

  default CustomerState mapState(String state) {
    return null == state ? null : switch (state) {
      case "active" -> CustomerState.ACTIVE;
      case "locked" -> CustomerState.LOCKED;
      case "disabled" -> CustomerState.DISABLED;
      default -> throw new IllegalStateException("Unexpected value: " + state);
    };
  }


}
