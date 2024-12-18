package de.schulung.spring.accounts.test.security;

import org.springframework.boot.test.context.TestComponent;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Retention(RetentionPolicy.RUNTIME)
@Target({
  ElementType.TYPE,
  ElementType.ANNOTATION_TYPE
})
@WithSecurityContext(factory = WithMockJwtUser.WithMockJwtUserSecurityContextFactory.class)
public @interface WithMockJwtUser {

  String username() default "user";

  String email() default "user@example.com";

  String[] roles() default {"USER"};

  @TestComponent
  class WithMockJwtUserSecurityContextFactory
    implements WithSecurityContextFactory<WithMockJwtUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockJwtUser annotation) {
      var context = SecurityContextHolder.createEmptyContext();

      var headers = Map.<String, Object>of(
        "alg", "HS256",
        "typ", "JWT"
      );
      var roles = Stream
        .of(annotation.roles())
        .map(role -> "ROLE_" + role.trim())
        .collect(Collectors.joining(","));
      var claims = Map.<String, Object>of(
        "iss", "https://mockissuer.com",
        "aud", "audience",
        "sub", annotation.username(),
        "name", annotation.username(),
        "email", annotation.email(),
        "roles", roles
      );
      var issuedAt = Instant.now();
      var expiresAt = issuedAt.plus(Duration.ofDays(1));
      var jwt = new Jwt("mockToken", issuedAt, expiresAt, headers, claims);

      var authentication = new JwtAuthenticationToken(jwt);
      authentication.setAuthenticated(true);
      authentication.setDetails(new WebAuthenticationDetails(new MockHttpServletRequest()));

      context.setAuthentication(authentication);

      return context;
    }

  }

}
