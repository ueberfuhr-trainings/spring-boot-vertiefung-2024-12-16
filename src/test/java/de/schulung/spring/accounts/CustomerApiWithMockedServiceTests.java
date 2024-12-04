package de.schulung.spring.accounts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerApiWithMockedServiceTests {

  @Autowired
  MockMvc mockMvc;
  @MockitoBean
  CustomersService customersService;

  @Test
  void shouldNotCreateCustomerWithoutBirthdate() throws Exception {
    mockMvc
      .perform(
        post("/customers")
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
                        {
                          "name": "Tom Mayer",
                          "state": "active"
                        }
            """)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isBadRequest());
    // so:
    verify(customersService, never()).createCustomer(any());
    // besser:
    verifyNoInteractions(customersService);
  }

  @Test
  void shouldReturnBadRequestWhenCreateCustomerWithUuid() throws Exception {
    mockMvc
      .perform(
        post("/customers")
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
                        {
                          "uuid": "f6debb95-2eda-495e-aa79-4c8a43a383cd",
                          "name": "Tom Mayer",
                          "birthdate": "1985-07-03",
                          "state": "active"
                        }
            """)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isBadRequest());
    // so:
    verify(customersService, never()).createCustomer(any());
    // besser:
    verifyNoInteractions(customersService);
  }

  @Test
  void shouldReturnBadRequestWhenCreateCustomerWithGelbeKatze() throws Exception {
    mockMvc
      .perform(
        post("/customers")
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
                        {
                          "name": "Tom Mayer",
                          "birthdate": "1985-07-03",
                          "state": "active",
                          "gelbekatze:": "miau"
                        }
            """)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isBadRequest());
    // so:
    verify(customersService, never()).createCustomer(any());
    // besser:
    verifyNoInteractions(customersService);

  }

  @Test
  void shouldReturn204ForSuccessfulDeletion() throws Exception {
    UUID uuid = UUID.randomUUID();
    when(customersService.deleteCustomer(uuid))
      .thenReturn(true);

    mockMvc
      .perform(
        delete("/customers/{id}", uuid)
      )
      .andExpect(status().isNoContent());

    verify(customersService).deleteCustomer(uuid);

  }

  @Test
  void shouldReturn404ForDeletionOfNonExisting() throws Exception {
    UUID uuid = UUID.randomUUID();
    when(customersService.deleteCustomer(uuid))
      .thenReturn(false);

    mockMvc
      .perform(
        delete("/customers/{id}", uuid)
      )
      .andExpect(status().isNotFound());

    verify(customersService).deleteCustomer(uuid);

  }

  @Test
  void shouldReturn404ForFindNonExisting() throws Exception {
    UUID uuid = UUID.randomUUID();
    when(customersService.findCustomer(uuid))
      .thenReturn(Optional.empty());

    mockMvc
      .perform(
        get("/customers/{id}", uuid)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isNotFound());

  }

  @Test
  void shouldReturnCustomerForFindById() throws Exception {
    UUID uuid = UUID.randomUUID();
    Customer customer = new Customer();
    customer.setUuid(uuid);
    customer.setName("Tom Mayer");
    customer.setState("active");
    customer.setBirthDate(LocalDate.of(1985, Month.JULY, 3));
    when(customersService.findCustomer(uuid))
      .thenReturn(Optional.of(customer));

    mockMvc
      .perform(
        get("/customers/{id}", uuid)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.uuid").value(uuid.toString()))
      .andExpect(jsonPath("$.name").value("Tom Mayer"))
      .andExpect(jsonPath("$.birthdate").value("1985-07-03"))
      .andExpect(jsonPath("$.state").value("active"));

  }


}
