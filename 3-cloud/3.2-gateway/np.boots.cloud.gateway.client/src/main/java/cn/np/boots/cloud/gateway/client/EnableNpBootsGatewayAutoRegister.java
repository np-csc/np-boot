package cn.np.boots.cloud.gateway.client;

import cn.np.boots.cloud.gateway.client.configurer.NpBootsGatewayAutoRegisterConfigurer;
import cn.np.boots.core.spring.auto.EnableNpAutoAfterApplicationStarted;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableNpAutoAfterApplicationStarted(action = NpBootsGatewayAutoRegisterConfigurer.class)
public @interface EnableNpBootsGatewayAutoRegister {
}
