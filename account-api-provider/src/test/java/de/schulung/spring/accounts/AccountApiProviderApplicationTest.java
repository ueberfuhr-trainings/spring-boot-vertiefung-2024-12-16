package de.schulung.spring.accounts;

import de.schulung.spring.accounts.shared.aspects.AutoConfigureLogPerformanceTest;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.RecordApplicationEvents;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A custom meta-annotation that is used for comprehensive integration testing of Spring Boot applications.
 * This annotation combines several commonly used testing annotations and configurations to simplify the setup
 * for testing the entire application context.
 * Use this annotation on test classes to reduce boilerplate configuration for integration tests
 * involving both web and database layers of the application.
 */
@Target({
  ElementType.TYPE,
  ElementType.ANNOTATION_TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
// Context Configuration
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@RecordApplicationEvents
@ActiveProfiles("test")
@AutoConfigureLogPerformanceTest
// optional, weil nicht Context-relevant
@Tag("springboot-test")
@Tag("account-api-provider-application-test")
public @interface AccountApiProviderApplicationTest {
}
