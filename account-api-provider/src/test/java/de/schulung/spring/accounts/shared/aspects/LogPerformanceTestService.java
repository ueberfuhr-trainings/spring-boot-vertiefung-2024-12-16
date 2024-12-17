package de.schulung.spring.accounts.shared.aspects;

import org.springframework.stereotype.Component;

// TODO @TestComponent?
@Component
public class LogPerformanceTestService {

  @LogPerformance
  void doSth() {
  }

}
