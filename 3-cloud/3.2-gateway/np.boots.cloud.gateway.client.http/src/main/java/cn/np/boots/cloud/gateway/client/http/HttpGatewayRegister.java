package cn.np.boots.cloud.gateway.client.http;

import org.dromara.soul.client.springmvc.annotation.SoulSpringMvcClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@ComponentScan
@SoulSpringMvcClient
public @interface HttpGatewayRegister {
    @AliasFor(annotation = SoulSpringMvcClient.class,attribute = "path")
    String path();

    String ruleName() default "";

    String desc() default "";

    String rpcType() default "http";

    boolean enabled() default true;
}
