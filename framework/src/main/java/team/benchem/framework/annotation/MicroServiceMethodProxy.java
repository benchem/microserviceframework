package team.benchem.framework.annotation;

import team.benchem.framework.lang.RequestType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MicroServiceMethodProxy {
    String microserviceKey();
    String path();
    RequestType type() default RequestType.GET;
}
