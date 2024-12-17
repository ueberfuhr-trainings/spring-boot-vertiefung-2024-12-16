package de.schulung.spring.accounts.shared.aspects;

import de.schulung.spring.accounts.shared.logging.MethodPerformanceLogger;
import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.InitializingBean;

@RequiredArgsConstructor
class LogPerformancePostProcessor
  extends AbstractBeanFactoryAwareAdvisingPostProcessor
  implements InitializingBean {

  private final Pointcut pointcut;
  private final MethodPerformanceLogger methodPerformanceLogger;

  private final MethodInterceptor interceptor = invocation -> {
    var ts = System.currentTimeMillis();
    try {
      return invocation.proceed();
    } finally {
      var ts2 = System.currentTimeMillis();
      LogPerformancePostProcessor.this.methodPerformanceLogger.logPerformance(
        invocation.getMethod(),
        ts2 - ts
      );
    }
  };

  @Override
  public void afterPropertiesSet() {
    this.advisor = new DefaultPointcutAdvisor(
      this.pointcut, // where?
      this.interceptor // what?
    );
  }
}