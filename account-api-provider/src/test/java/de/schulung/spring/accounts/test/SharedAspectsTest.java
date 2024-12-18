package de.schulung.spring.accounts.test;

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
// These tests use the same context as whole application tests
@WholeApplicationTest
// optional, weil nicht Context-relevant
@Tag("shared-aspects-test")
public @interface SharedAspectsTest {
}
