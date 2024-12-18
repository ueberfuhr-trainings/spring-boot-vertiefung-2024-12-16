package de.schulung.spring.accounts.shared.aspects;

import de.schulung.spring.accounts.shared.logging.MethodPerformanceLogger;
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

@Target({
  ElementType.TYPE,
  ElementType.ANNOTATION_TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
// Context Configuration
@Import({
  LogPerformanceTestService.class,
  LogPerformanceTestAtClassLevelService.class,
  ConfigureLogPerformanceTest.MockConfiguration.class
})
public @interface ConfigureLogPerformanceTest {

  @TestConfiguration
  class MockConfiguration {

    @Primary
    @Bean
    MethodPerformanceLogger methodPerformanceLoggerMock() {
      return Mockito.mock(
        MethodPerformanceLogger.class,
        MockReset.withSettings(MockReset.AFTER)
      );
    }

  }

}
