package de.schulung.spring.accounts.client;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Flux;

@Validated
@HttpExchange("/customers")
public interface CustomerApiClient {


  @GetExchange
  Flux<CustomerDto> getCustomers();

  @GetExchange
  Flux<CustomerDto> getCustomersByState(
    @RequestParam("state")
    @NotNull
    @Pattern(regexp = "active|locked|disabled")
    String state
  );

  @PostExchange
  void addCustomer(
    @Valid
    CustomerDto customer
  );

}
