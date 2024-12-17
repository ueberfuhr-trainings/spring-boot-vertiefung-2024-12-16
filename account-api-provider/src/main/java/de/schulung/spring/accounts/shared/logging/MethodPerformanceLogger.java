package de.schulung.spring.accounts.shared.logging;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Slf4j(topic = "logger.performance")
public class MethodPerformanceLogger {

  @Getter
  @Builder
  public static class LoggingContext {
    @Builder.Default
    private Level level = Level.INFO;
    private Method method;
    private long duration;
  }

  public void logPerformance(LoggingContext loggingContext) {
    log
      .atLevel(loggingContext.getLevel())
      .log(
        "Methode {} brauchte {} ms.",
        loggingContext.getMethod().getName(),
        loggingContext.getDuration()
      );
  }

}
