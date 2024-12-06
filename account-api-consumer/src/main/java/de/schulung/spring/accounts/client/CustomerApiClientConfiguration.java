package de.schulung.spring.accounts.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class CustomerApiClientConfiguration {

  @Bean
  CustomerApiClient customerApiClient(
    @Value("${client.baseurl}")
    String baseUrl
  ) {
    var webClient = WebClient
      .builder()
      .baseUrl(baseUrl)
      .build();
    var restClientAdapter = WebClientAdapter
      .create(webClient);
    return HttpServiceProxyFactory
      .builderFor(restClientAdapter)
      .build()
      .createClient(CustomerApiClient.class);
  }


}
