package de.schulung.spring.accounts;

import de.schulung.spring.accounts.client.CustomerApiClient;
import de.schulung.spring.accounts.client.CustomerDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

@Validated
@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerApiClient client;

  @Cacheable("customers") // does not work here!
  public Flux<CustomerDto> getCustomersByState(
    @NotNull
    @Pattern(regexp = "active|locked|disabled")
    String state
  ) {
    return client
      .getCustomersByState(state)
      .cache(); // both in combination
  }

}
