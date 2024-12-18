package de.schulung.spring.accounts.test;

import de.schulung.spring.accounts.boundary.CustomersController;
import de.schulung.spring.accounts.domain.CustomersService;
import org.junit.jupiter.api.Tag;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockReset;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({
  ElementType.TYPE,
  ElementType.ANNOTATION_TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
// Context Configuration
@WebMvcTest
@ComponentScan(
  basePackageClasses = CustomersController.class
)
@ActiveProfiles("test")
@Import(BoundaryTest.MockConfiguration.class)
// @MockBean(classes = CustomersService.class)
// optional, weil nicht Context-relevant
@Tag("springboot-test")
@Tag("boundary-tests")
public @interface BoundaryTest {

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
