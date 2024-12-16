package de.schulung.spring.accounts.boundary;

import de.schulung.spring.accounts.boundary.validation.CustomerStatePattern;
import de.schulung.spring.accounts.domain.CustomersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;
import java.util.stream.Stream;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomersController {

  private final CustomersService customersService;
  private final CustomerDtoMapper mapper;

  @GetMapping(
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  Stream<CustomerDto> getAllCustomers(
    @CustomerStatePattern
    @RequestParam(required = false, name = "state")
    String stateFilter
  ) {
    return
      (
        stateFilter != null
          ? customersService.findCustomers(mapper.mapState(stateFilter))
          : customersService.findCustomers()
      )
        .map(mapper::map);
  }

  @GetMapping(
    path = "/{id}",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  CustomerDto getCustomerById(
    @PathVariable("id")
    UUID uuid
  ) {
    return customersService
      .findCustomer(uuid)
      .map(mapper::map)
      .orElseThrow(NotFoundException::new);
  }


  @PostMapping(
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  ResponseEntity<CustomerDto> createCustomer(
    @Valid
    @RequestBody
    CustomerDto customerDto
  ) {
    var customer = mapper.map(customerDto);
    customersService.createCustomer(customer);
    var responseDto = mapper.map(customer);
    // Location-Header
    var location = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(customer.getUuid())
      .toUri();
    // Response
    return ResponseEntity
      .created(location)
      .body(responseDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void deleteCustomer(
    @PathVariable("id")
    UUID uuid
  ) {
    if (!customersService.deleteCustomer(uuid)) {
      throw new NotFoundException();
    }
  }

  @PutMapping(
    path = "/{id}",
    consumes = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void replaceCustomer(
    @PathVariable("id")
    UUID uuid,
    @Valid
    @RequestBody
    CustomerDto customerDto
  ) {
    var customer = mapper.map(customerDto);
    customer.setUuid(uuid);
    if (!customersService.updateCustomer(customer)) {
      throw new NotFoundException();
    }
  }

}
