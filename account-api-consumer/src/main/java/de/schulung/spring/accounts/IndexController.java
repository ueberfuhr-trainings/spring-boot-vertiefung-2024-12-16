package de.schulung.spring.accounts;

import de.schulung.spring.accounts.client.CustomerApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {

  private final CustomerApiClient client;

  @GetMapping(
    produces = MediaType.TEXT_PLAIN_VALUE
  )
  @ResponseBody
  Flux<String> listActiveCustomerNames() {
    return client.getCustomersByState("active")
      .map(customer -> customer.getName() + "\n");
  }

}
