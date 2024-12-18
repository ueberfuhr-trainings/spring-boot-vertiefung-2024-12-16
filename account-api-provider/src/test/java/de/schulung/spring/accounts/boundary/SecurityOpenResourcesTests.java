package de.schulung.spring.accounts.boundary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class SecurityOpenResourcesTests {

  @Autowired
  MockMvc mockMvc;

  @Test
  void shouldAllowOpenAccessToIndexHtml() throws Exception {
    mockMvc
      .perform(
        get("/index.html")
          .accept(MediaType.ALL)
      )
      .andExpect(status().isOk());
  }

  @Test
  void shouldAllowOpenAccessToOpenApi() throws Exception {
    mockMvc
      .perform(
        get("/openapi.yml")
          .accept(MediaType.ALL)
      )
      .andExpect(status().isOk());
  }

  @Test
  void shouldNotAllowAccessToApi() throws Exception {
    mockMvc
      .perform(
        get("/customers")
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isUnauthorized());
  }
}
