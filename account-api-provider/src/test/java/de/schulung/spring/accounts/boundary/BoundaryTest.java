package de.schulung.spring.accounts.boundary;

import de.schulung.spring.accounts.domain.AutoConfigureDomainMocks;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

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
// Context Configuration
@WebMvcTest
@ComponentScan(basePackageClasses = BoundaryTest.class)
@AutoConfigureDomainMocks
@ActiveProfiles("test")
// optional, weil nicht Context-relevant
@Tag("springboot-test")
@Tag("boundary-tests")
public @interface BoundaryTest {

}
