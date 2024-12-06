package de.schulung.spring.accounts.boundary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class CustomerApiTests {

  @Autowired
  MockMvc mockMvc;

  @Test
  void shouldReturnCustomers() throws Exception {
    mockMvc
      .perform(
        get("/customers")
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(content().string(startsWith("[")))
      .andExpect(content().string(endsWith("]")));
  }

  @Test
  void shouldNotReturnCustomersWithInvalidState() throws Exception {
    mockMvc
      .perform(
        get("/customers")
          .param("state", "gelbekatze")
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isBadRequest());
  }

  /*
   * Testfall:
   *  - POST /customers mit {name, birthdate,state}
   *    -> 201 Statuscode
   */
  @Test
  void shouldCreateAndReturnCustomer() throws Exception {
    var location = mockMvc
      .perform(
        post("/customers")
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
                        {
                          "name": "Tom Mayer",
                          "birthdate": "1985-07-03",
                          "state": "active"
                        }
            """)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isCreated())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.name").value("Tom Mayer"))
      .andExpect(jsonPath("$.birthdate").value("1985-07-03"))
      .andExpect(jsonPath("$.state").value("active"))
      .andExpect(jsonPath("$.uuid").exists())
      .andExpect(header().exists("Location"))
      .andReturn()
      .getResponse()
      .getHeader("Location");

    assertThat(location).isNotBlank();

    mockMvc
      .perform(
        get(location)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.name").value("Tom Mayer"))
      .andExpect(jsonPath("$.birthdate").value("1985-07-03"))
      .andExpect(jsonPath("$.state").value("active"));

  }

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
  }

  @Test
  void shouldDeleteCustomer() throws Exception {
    var location = mockMvc
      .perform(
        post("/customers")
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
                        {
                          "name": "Tom Mayer",
                          "birthdate": "1985-07-03",
                          "state": "active"
                        }
            """)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isCreated())
      .andExpect(header().exists("Location"))
      .andReturn()
      .getResponse()
      .getHeader("Location");

    assertThat(location).isNotBlank();

    // DELETE {location} -> 204
    mockMvc
      .perform(
        delete(location)
      )
      .andExpect(status().isNoContent());

    // DELETE {location} -> 404
    mockMvc
      .perform(
        delete(location)
      )
      .andExpect(status().isNotFound());

    // GET {location} -> 404
    mockMvc
      .perform(
        get(location)
      )
      .andExpect(status().isNotFound());

  }

  @Test
  void shouldUpdateAndReturnCustomer() throws Exception {
    // first, we need to create a customer
    var location = mockMvc
      .perform(
        post("/customers")
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
                        {
                          "name": "Tom Mayer",
                          "birthdate": "1985-07-03",
                          "state": "active"
                        }
            """)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isCreated())
      .andReturn()
      .getResponse()
      .getHeader("Location");

    assertThat(location).isNotBlank();

    mockMvc
      .perform(
        put(location)
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
                        {
                          "name": "Julia Mayer",
                          "birthdate": "1984-07-03",
                          "state": "disabled"
                        }
            """)
      )
      .andExpect(status().isNoContent());

    mockMvc
      .perform(
        get(location)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.name").value("Julia Mayer"))
      .andExpect(jsonPath("$.birthdate").value("1984-07-03"))
      .andExpect(jsonPath("$.state").value("disabled"));

  }

  @Test
  void shouldReturn404OnUpdateMissingCustomer() throws Exception {
    // first, we need to create a customer
    var location = mockMvc
      .perform(
        post("/customers")
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
                        {
                          "name": "Tom Mayer",
                          "birthdate": "1985-07-03",
                          "state": "active"
                        }
            """)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isCreated())
      .andReturn()
      .getResponse()
      .getHeader("Location");

    assertThat(location).isNotBlank();

    // Now, we delete it
    mockMvc.perform(
        delete(location)
      )
      .andExpect(status().isNoContent());

    // Not, we can expect 404
    mockMvc
      .perform(
        put(location)
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
                        {
                          "name": "Julia Mayer",
                          "birthdate": "1984-07-03",
                          "state": "disabled"
                        }
            """)
      )
      .andExpect(status().isNotFound());

  }

  @Test
  void shouldNotReturnCustomerOnNonMatchingStateFilter() throws Exception {
    // create a customer with state active
    // first, we need to create a customer
    mockMvc
      .perform(
        post("/customers")
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
                        {
                          "name": "Active Tom Mayer",
                          "birthdate": "1985-07-03",
                          "state": "active"
                        }
            """)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isCreated());

    // request for locked customers
    mockMvc
      .perform(
        get("/customers")
          .param("state", "locked")
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[?(@.name=='Active Tom Mayer')]").doesNotExist());
  }

  @Test
  void shouldReturnCustomerOnMatchingStateFilter() throws Exception {
    // create a customer with state active
    // first, we need to create a customer
    mockMvc
      .perform(
        post("/customers")
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
                        {
                          "name": "Active Tom Mayer",
                          "birthdate": "1985-07-03",
                          "state": "active"
                        }
            """)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isCreated());

    // request for active customers
    mockMvc
      .perform(
        get("/customers")
          .param("state", "active")
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[?(@.name=='Active Tom Mayer')]").exists());
  }

}
