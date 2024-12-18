package de.schulung.spring.accounts.boundary.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@ConditionalOnProperty(
  name = "application.security.enabled",
  havingValue = "true",
  matchIfMissing = true
)
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
  // prePostEnabled = true, // activated by default
  securedEnabled = true, // @Secured
  jsr250Enabled = true // @RolesAllowed
)
public class SecurityConfiguration {

  // Security Filter Chain:
  // - https://docs.spring.io/spring-security/reference/servlet/architecture.html
  // - https://medium.com/@tanmaysaxena2904/spring-security-the-security-filter-chain-e09e1f53b73d
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
      // this would apply this chain only for public resources
      // .securityMatcher("/public/**")
      // we need to disable stateful authentication
      .csrf(AbstractHttpConfigurer::disable)
      .sessionManagement(
        configurer -> configurer
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      // enable JWT-encoded bearer token support
      .oauth2ResourceServer(
        configurer -> configurer
          .jwt(withDefaults())
      )
      // authorize requests
      .authorizeHttpRequests(
        customizer -> customizer
          .requestMatchers("/index.html", "/openapi.yml").permitAll()
          .requestMatchers("/actuator", "/actuator/**").permitAll() // protect by using another management port !!!
          .requestMatchers("/customers", "customers/**").authenticated()
      )
      .build();
  }

}
