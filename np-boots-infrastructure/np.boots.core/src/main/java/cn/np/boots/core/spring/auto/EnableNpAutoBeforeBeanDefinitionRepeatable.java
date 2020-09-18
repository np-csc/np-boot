package cn.np.boots.core.spring.auto;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import(NpBootsApplicationLifecycle.class)
public @interface EnableNpAutoBeforeBeanDefinitionRepeatable {
    EnableNpAutoBeforeBeanDefinition[] value();
}
