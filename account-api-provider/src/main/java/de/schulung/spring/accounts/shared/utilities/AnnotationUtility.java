package de.schulung.spring.accounts.shared.utilities;

import lombok.experimental.UtilityClass;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;

@UtilityClass
public class AnnotationUtility {

  public <T extends Annotation> Optional<T> findAnnotation(Method method, Class<T> annotationType) {
    var methodAnnotation = AnnotationUtils.findAnnotation(method, annotationType);
    return
      null != methodAnnotation
        ? Optional.of(methodAnnotation)
        : Optional.ofNullable(AnnotationUtils.findAnnotation(method.getDeclaringClass(), annotationType));
  }

}
