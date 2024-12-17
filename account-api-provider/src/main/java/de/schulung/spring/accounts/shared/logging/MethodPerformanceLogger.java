package de.schulung.spring.accounts.shared.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Slf4j(topic = "logger.performance")
public class MethodPerformanceLogger {

  public void logPerformance(Method method, long duration) {
    log.info(
      "Methode {} brauchte {} ms.",
      method.getName(),
      duration
    );
  }

}
