package de.schulung.spring.accounts;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {

  private final CustomerService service;

  @GetMapping(
    produces = MediaType.TEXT_PLAIN_VALUE
  )
  @ResponseBody
  Flux<String> listActiveCustomerNames() {
    return service
      .getCustomersByState("active")
      .map(customer -> customer.getName() + "\n");
  }

}
