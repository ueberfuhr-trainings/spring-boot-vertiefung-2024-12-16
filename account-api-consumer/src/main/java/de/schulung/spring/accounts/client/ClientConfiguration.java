package de.schulung.spring.accounts.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(
  prefix = "client"
)
@EnableCaching
// https://www.baeldung.com/spring-cache-tutorial
public class ClientConfiguration {

  private String baseurl = "http://localhost:8080";
  private long timeout = 1000;
  private int connectionTimeout = 1000;

}
