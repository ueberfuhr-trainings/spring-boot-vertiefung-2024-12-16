package de.schulung.spring.accounts.domain;

import org.junit.jupiter.api.Tag;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({
  ElementType.TYPE,
  ElementType.ANNOTATION_TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DomainTest
@TestPropertySource(
  properties = {
    "customers.initialization.enabled=true"
  }
)
@Tag("domain-test-with-initialization")
public @interface DomainTestWithInitializationEnabled {
}
