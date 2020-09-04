package cn.np.boots.cloud.gateway.client.springmvc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface SpringMvcGatewayRegister {
    String path() default "";
    String desc() default "";
    boolean enabled() default true;
}
