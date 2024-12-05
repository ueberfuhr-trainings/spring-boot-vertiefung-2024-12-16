package de.schulung.spring.accounts.boundary;

import de.schulung.spring.accounts.domain.Customer;
import de.schulung.spring.accounts.domain.CustomerState;
import org.springframework.stereotype.Component;

@Component
public class CustomerDtoMapper {

  public Customer map(CustomerDto customerDto) {
    return Customer
      .builder()
      .uuid(customerDto.getUuid())
      .name(customerDto.getName())
      .birthDate(customerDto.getBirthDate())
      .state(mapState(customerDto.getState()))
      .build();
  }

  public CustomerDto map(Customer customer) {
    var result = new CustomerDto();
    result.setUuid(customer.getUuid());
    result.setName(customer.getName());
    result.setBirthDate(customer.getBirthDate());
    result.setState(mapState(customer.getState()));
    return result;
  }

  public String mapState(CustomerState state) {
    return null == state ? null : switch (state) {
      case ACTIVE -> "active";
      case LOCKED -> "locked";
      case DISABLED -> "disabled";
    };
  }

  public CustomerState mapState(String state) {
    return null == state ? null : switch (state) {
      case "active" -> CustomerState.ACTIVE;
      case "locked" -> CustomerState.LOCKED;
      case "disabled" -> CustomerState.DISABLED;
      default -> throw new IllegalStateException("Unexpected value: " + state);
    };
  }


}
