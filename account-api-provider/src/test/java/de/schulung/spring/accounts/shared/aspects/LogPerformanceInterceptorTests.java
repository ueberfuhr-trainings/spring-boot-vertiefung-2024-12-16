package de.schulung.spring.accounts.shared.aspects;

import de.schulung.spring.accounts.shared.logging.MethodPerformanceLogger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class LogPerformanceInterceptorTests {

  @Autowired
  LogPerformanceTestService logPerformanceTestService;
  @MockitoBean
  MethodPerformanceLogger methodPerformanceLogger;

  @Test
  void shouldInvokeMethodPerformanceLogger() throws NoSuchMethodException {
    logPerformanceTestService.doSth();
    verify(methodPerformanceLogger).logPerformance(
      eq(LogPerformanceTestService.class.getDeclaredMethod("doSth")),
      anyLong()
    );
  }

}
