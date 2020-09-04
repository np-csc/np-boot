package cn.np.boots.core.spring.auto.configurer;

import lombok.Data;
import org.springframework.core.annotation.MergedAnnotation;

@Data
public abstract class NpSpringAutoAfterBeanInitializedConfigurer {
    protected MergedAnnotation<?> annotation;
    public abstract void doAction(final Object bean, final String beanName);
}
