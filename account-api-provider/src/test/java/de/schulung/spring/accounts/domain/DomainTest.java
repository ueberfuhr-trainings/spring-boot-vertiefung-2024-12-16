package de.schulung.spring.accounts.domain;

import de.schulung.spring.accounts.AccountApiProviderApplicationTest;
import org.junit.jupiter.api.Tag;

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
// Domain tests use the same context as whole application tests
@AccountApiProviderApplicationTest
// optional, weil nicht Context-relevant
@Tag("domain-test")
public @interface DomainTest {
}
