package cn.np.boots.core.spring.auto.configurer;

import lombok.Data;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.core.annotation.MergedAnnotation;

@Data
public abstract class NpSpringAutoBeforeBeanDefinitionConfigurer {
    protected MergedAnnotation<?> annotation;
    protected BeanDefinitionRegistry registry;
    protected BeanNameGenerator importBeanNameGenerator;
    public abstract void doAction();
}
