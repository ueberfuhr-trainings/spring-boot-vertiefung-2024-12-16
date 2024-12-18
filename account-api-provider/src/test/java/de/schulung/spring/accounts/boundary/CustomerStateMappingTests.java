package de.schulung.spring.accounts.boundary;

import de.schulung.spring.accounts.domain.Customer;
import de.schulung.spring.accounts.domain.CustomerState;
import de.schulung.spring.accounts.domain.CustomersService;
import de.schulung.spring.accounts.test.BoundaryTest;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.stubbing.Stubber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@BoundaryTest
class CustomerStateMappingTests {

  @Autowired
  MockMvc mockMvc;
  @Autowired // Mock
  CustomersService service;

  private static class CustomerStateMappings implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
      return Stream.of(
        Arguments.of("active", CustomerState.ACTIVE),
        Arguments.of("locked", CustomerState.LOCKED),
        Arguments.of("disabled", CustomerState.DISABLED)
      );
    }
  }

  private static Stubber doSetUuidInCustomerToAvoidNPE() {
    return doAnswer(invocation -> {
      ((Customer) invocation.getArgument(0))
        .setUuid(UUID.randomUUID());
      return null;
    });
  }

  @ParameterizedTest
  @ArgumentsSource(CustomerStateMappings.class)
  void shouldMapStateCorrectlyToDomain(String inputValue, CustomerState wantedState) throws Exception {
    doSetUuidInCustomerToAvoidNPE()
      .when(service)
      .createCustomer(any());

    mockMvc
      .perform(
        post("/customers")
          .contentType(MediaType.APPLICATION_JSON)
          .content(String.format("""
                        {
                          "name": "Tom Mayer",
                          "birthdate": "1985-07-03",
                          "state": "%s"
                        }
            """, inputValue))
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isCreated());

    verify(service).createCustomer(
      argThat(customer -> customer.getState() == wantedState)
    );

  }

  @ParameterizedTest
  @ArgumentsSource(CustomerStateMappings.class)
  void shouldMapStateCorrectlyFromDomain(String wantedValue, CustomerState currentState) throws Exception {
    var uuid = UUID.randomUUID();
    var customer = Customer
      .builder()
      .uuid(uuid)
      .name("Tom Mayer")
      .birthDate(LocalDate.of(1985, Month.JULY, 3))
      .state(currentState)
      .build();
    when(service.findCustomer(any()))
      .thenReturn(Optional.of(customer));

    mockMvc.perform(
        get("/customers/{id}", uuid)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.state").value(wantedValue));

  }

}
