package cn.np.boots.core.spring.auto;

import cn.np.boots.core.spring.auto.configurer.NpSpringAutoAfterBeanInitializedConfigurer;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Repeatable(EnableNpAutoAfterBeanInitializedRepeatable.class)
@Import(NpBootsApplicationLifecycle.class)
public @interface EnableNpAutoAfterBeanInitialized {
    int order() default 0;

    Class<?>[] dependOn() default {};

    Class<? extends NpSpringAutoAfterBeanInitializedConfigurer> action();

    String actionBeanName() default "";
}
