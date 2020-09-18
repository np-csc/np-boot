package cn.np.boots.core.spring.auto;

import cn.np.boots.core.spring.auto.configurer.NpSpringAutoBeforeBeanDefinitionConfigurer;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Repeatable(EnableNpAutoBeforeBeanDefinitionRepeatable.class)
@Import(NpBootsApplicationLifecycle.class)
public @interface EnableNpAutoBeforeBeanDefinition {
    int order() default 0;

    Class<?>[] dependOn() default {};

    Class<? extends NpSpringAutoBeforeBeanDefinitionConfigurer> action();

    String actionBeanName() default "";
}
