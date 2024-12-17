package de.schulung.spring.accounts.shared.aspects;

import org.slf4j.event.Level;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
@LogPerformance(Level.DEBUG)
public class LogPerformanceTestAtClassLevelService {

  void doSthDebug() {
  }


}
