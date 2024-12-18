package de.schulung.spring.accounts.domain;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.bean.override.mockito.MockReset;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
// @MockBean(classes = CustomersService.class)
@Import(AutoConfigureDomainMocks.MockConfiguration.class)
public @interface AutoConfigureDomainMocks {

  @TestConfiguration
  class MockConfiguration {

    @Primary
    @Bean
    CustomersService customersServiceMock() {
      return Mockito.mock(
        CustomersService.class,
        MockReset.withSettings(MockReset.AFTER)
      );
    }

  }

}