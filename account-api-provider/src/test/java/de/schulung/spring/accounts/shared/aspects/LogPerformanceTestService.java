package de.schulung.spring.accounts.shared.aspects;

import org.slf4j.event.Level;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class LogPerformanceTestService {

  @LogPerformance
  void doSth() {
  }

  @LogPerformance(Level.DEBUG)
  void doSthDebug() {
  }


}
