package cn.np.boots.core.spring.auto;

import cn.np.boots.core.spring.auto.configurer.NpSpringAutoAfterApplicationStartedConfigurer;
import cn.np.boots.core.spring.auto.core.NpSpringAutoRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Repeatable(EnableNpAutoAfterApplicationStartedRepeatable.class)
@Import(NpSpringAutoRegistrar.class)
public @interface EnableNpAutoAfterApplicationStarted {

    int order() default 0;

    Class<?>[] dependOn() default {};

    Class<? extends NpSpringAutoAfterApplicationStartedConfigurer> action();

    String actionBeanName() default "";
}
