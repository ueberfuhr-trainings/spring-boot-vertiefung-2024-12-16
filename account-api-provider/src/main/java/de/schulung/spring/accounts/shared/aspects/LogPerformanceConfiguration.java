package de.schulung.spring.accounts.shared.aspects;

import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LogPerformanceConfiguration {

  @Bean
  LogPerformancePostProcessor logPerformancePostProcessorOnMethodLevel() {
    return new LogPerformancePostProcessor(
      new AnnotationMatchingPointcut(
        null,
        LogPerformance.class,
        true
      )
    );
  }

  @Bean
  LogPerformancePostProcessor logPerformancePostProcessorOnClassLevel() {
    return new LogPerformancePostProcessor(
      new AnnotationMatchingPointcut(
        LogPerformance.class,
        true
      )
    );
  }

}
