package de.schulung.spring.accounts.shared.aspects;

import de.schulung.spring.accounts.shared.logging.MethodPerformanceLogger;
import de.schulung.spring.accounts.shared.logging.MethodPerformanceLogger.LoggingContext;
import org.junit.jupiter.api.Test;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.verify;

@SharedAspectsTest
public class LogPerformanceInterceptorTests {

  @Autowired
  LogPerformanceTestService logPerformanceTestService;
  @Autowired
  LogPerformanceTestAtClassLevelService logPerformanceTestAtClassLevelService;
  @Autowired // Mock
  MethodPerformanceLogger methodPerformanceLogger;

  @Test
  void shouldInvokeMethodPerformanceLogger() {
    logPerformanceTestService.doSth();
    verify(methodPerformanceLogger).logPerformance(
      assertArg(
        context ->
          assertThat(context)
            .extracting(
              LoggingContext::getLevel,
              LoggingContext::getMethod
            )
            .containsExactly(
              Level.INFO,
              LogPerformanceTestService.class.getDeclaredMethod("doSth")
            )
      )
    );
  }

  @Test
  void shouldInvokeMethodPerformanceLoggerDebugLevel() {
    logPerformanceTestService.doSthDebug();
    verify(methodPerformanceLogger).logPerformance(
      assertArg(
        context ->
          assertThat(context)
            .extracting(
              LoggingContext::getLevel,
              LoggingContext::getMethod
            )
            .containsExactly(
              Level.DEBUG,
              LogPerformanceTestService.class.getDeclaredMethod("doSthDebug")
            )
      )
    );
  }

  @Test
  void shouldInvokeClassPerformanceLoggerDebugLevel() {
    logPerformanceTestAtClassLevelService.doSthDebug();
    verify(methodPerformanceLogger).logPerformance(
      assertArg(
        context ->
          assertThat(context)
            .extracting(
              LoggingContext::getLevel,
              LoggingContext::getMethod
            )
            .containsExactly(
              Level.DEBUG,
              LogPerformanceTestAtClassLevelService.class.getDeclaredMethod("doSthDebug")
            )
      )
    );
  }

}
