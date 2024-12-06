package de.schulung.spring.accounts.client;

import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class CustomerApiClientConfiguration {

  @Bean
  HttpClient httpClient(
    @Value("${client.connect-timeout:1000}")
    int connectTimeout,
    @Value("${client.timeout:1000}")
    int timeout
  ) {
    return HttpClient
      .create()
      .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
      .responseTimeout(Duration.ofMillis(timeout));
  }

  @Bean
  WebClient.Builder webClientBuilder(HttpClient httpClient) {
    return WebClient
      .builder()
      .clientConnector(new ReactorClientHttpConnector(httpClient));
  }

  @Bean
  CustomerApiClient customerApiClient(
    WebClient.Builder webClientBuilder,
    @Value("${client.baseurl}")
    String baseUrl
  ) {
    var webClient = webClientBuilder
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
