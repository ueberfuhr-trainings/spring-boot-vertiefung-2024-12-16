package de.schulung.spring.accounts.test.security;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({
  ElementType.TYPE,
  ElementType.ANNOTATION_TYPE
})
@TestPropertySource(
  properties = {
    "application.security.enabled=false"
  }
)
@Import(WithSecurityDisabled.SecurityConfiguration.class)
public @interface WithSecurityDisabled {

  @TestConfiguration
  class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
          configurer -> configurer
            .anyRequest().permitAll()
        );
      return http.build();
    }

  }

}
