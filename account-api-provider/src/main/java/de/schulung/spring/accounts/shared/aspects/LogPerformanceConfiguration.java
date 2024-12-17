package de.schulung.spring.accounts.shared.aspects;

import de.schulung.spring.accounts.shared.logging.MethodPerformanceLogger;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LogPerformanceConfiguration {

  @Bean
  LogPerformancePostProcessor logPerformancePostProcessorOnMethodLevel(
    MethodPerformanceLogger methodPerformanceLogger
  ) {
    return new LogPerformancePostProcessor(
      new AnnotationMatchingPointcut(
        null,
        LogPerformance.class,
        true
      ),
      methodPerformanceLogger
    );
  }

  @Bean
  LogPerformancePostProcessor logPerformancePostProcessorOnClassLevel(
    MethodPerformanceLogger methodPerformanceLogger
  ) {
    return new LogPerformancePostProcessor(
      new AnnotationMatchingPointcut(
        LogPerformance.class,
        true
      ),
      methodPerformanceLogger
    );
  }

}
