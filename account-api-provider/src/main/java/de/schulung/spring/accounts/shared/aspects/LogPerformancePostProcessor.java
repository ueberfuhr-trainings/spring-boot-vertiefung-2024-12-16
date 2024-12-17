package de.schulung.spring.accounts.shared.aspects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.InitializingBean;

@Slf4j
@RequiredArgsConstructor
class LogPerformancePostProcessor
  extends AbstractBeanFactoryAwareAdvisingPostProcessor
  implements InitializingBean {

  private final Pointcut pointcut;

  private final MethodInterceptor interceptor = invocation -> {
    var ts = System.currentTimeMillis();
    try {
      return invocation.proceed();
    } finally {
      var ts2 = System.currentTimeMillis();
      log.info(
        "Methode {} brauchte {} ms.",
        invocation.getMethod().getName(),
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
