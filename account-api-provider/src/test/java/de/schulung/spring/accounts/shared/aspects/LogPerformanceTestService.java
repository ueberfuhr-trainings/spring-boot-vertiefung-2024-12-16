package de.schulung.spring.accounts.shared.aspects;

import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class LogPerformanceTestService {

  @LogPerformance
  void doSth() {
  }

}
