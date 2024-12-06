package de.schulung.spring.accounts.domain;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomersSinkConfiguration {

  @ConditionalOnMissingBean
  @Bean
  CustomersSink inMemoryCustomersSink() {
    return new CustomersSinkInMemoryImpl();
  }

}
