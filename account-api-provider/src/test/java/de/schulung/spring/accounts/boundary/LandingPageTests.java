package de.schulung.spring.accounts.boundary;

import de.schulung.spring.accounts.AccountApiProviderApplicationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.endsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AccountApiProviderApplicationTest
class LandingPageTests {

  @Autowired
  MockMvc mockMvc;

  @Test
  void shouldRedirectToIndexPage() throws Exception {
    mockMvc.perform(
        get("/")
          .accept(MediaType.TEXT_HTML)
      )
      .andExpect(status().isFound())
      .andExpect(header().string("Location", endsWith("/index.html")));
  }

  @Test
  void shouldHaveIndexHtml() throws Exception {
    mockMvc
      .perform(
        get("/index.html")
          .accept(MediaType.ALL)
      )
      .andExpect(status().isOk());
  }

}
