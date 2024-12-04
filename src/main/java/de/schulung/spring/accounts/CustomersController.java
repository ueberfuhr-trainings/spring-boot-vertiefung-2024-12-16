package de.schulung.spring.accounts;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@RestController
@RequestMapping("/customers")
public class CustomersController {

  private final Map<UUID, Customer> customers = new ConcurrentHashMap<>();

  @GetMapping(
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  Stream<Customer> getAllCustomers(
    @Pattern(regexp = "active|locked|disabled")
    @RequestParam(required = false, name = "state")
    String stateFilter
  ) {
    return customers
      .values()
      .stream();
  }

  @GetMapping(
    path = "/{id}",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  Customer getCustomerById(
    @PathVariable("id")
    UUID uuid
  ) {
    return Optional
      .ofNullable(customers.get(uuid))
      .orElseThrow(NotFoundException::new);
  }


  @PostMapping(
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  ResponseEntity<Customer> createCustomer(
    @Valid
    @RequestBody
    Customer customer
  ) {
    customer.setUuid(UUID.randomUUID());
    customers.put(customer.getUuid(), customer);
    // Location-Header
    var location = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(customer.getUuid())
      .toUri();
    // Response
    return ResponseEntity
      .created(location)
      .body(customer);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void deleteCustomer(
    @PathVariable("id")
    UUID uuid
  ) {
    if (customers.remove(uuid) == null) {
      throw new NotFoundException();
    }
  }

}
