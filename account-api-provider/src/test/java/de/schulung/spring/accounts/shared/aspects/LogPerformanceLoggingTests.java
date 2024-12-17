package de.schulung.spring.accounts.shared.aspects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
@Import(LogPerformanceTestService.class)
public class LogPerformanceLoggingTests {

  @Autowired
  LogPerformanceTestService logPerformanceTestService;

  @Test
  void shouldLogPerformance(CapturedOutput output) {
    var logLength = output.length();
    logPerformanceTestService.doSth();
    var newLog = output.subSequence(logLength, output.length() - 1);
    assertThat(newLog).contains("Methode doSth brauchte");
  }
}
