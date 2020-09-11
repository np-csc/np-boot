package cn.np.boots.common.utils.spring.bean;

import cn.np.boots.common.utils.NpUtils;
import cn.np.boots.core.pattern.NpPattern;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

@Component
public class NpSpringBeanDefinitionUtils {
    public Class<?> resolveBeanClassType(BeanDefinition beanDefinition) {
        Class<?> clazz = null;
        if (beanDefinition instanceof AnnotatedBeanDefinition) {
            String className;
            if (this.isConfigurationSource(beanDefinition)) {
                MethodMetadata methodMetadata = ((AnnotatedBeanDefinition) beanDefinition).getFactoryMethodMetadata();
                className = methodMetadata.getReturnTypeName();
            } else {
                AnnotationMetadata annotationMetadata = ((AnnotatedBeanDefinition) beanDefinition)
                        .getMetadata();
                className = annotationMetadata.getClassName();
            }

            clazz = NpPattern.tryCatchGet(()->
                    NpUtils.type().string().isEmpty(className)?null:NpUtils.reflect().classForName(className,null));
        }

        if(clazz == null){
            try {
                clazz = ((AbstractBeanDefinition) beanDefinition).getBeanClass();
            } catch (IllegalStateException ex) {
                clazz = NpPattern.tryCatchGet(()->{
                    String className = beanDefinition.getBeanClassName();
                    return StringUtils.isEmpty(className) ? null : NpUtils.reflect().classForName(className,null);
                });
            }
        }

        if(NpUtils.reflect().isCglibProxyClass(clazz)){
            return clazz.getSuperclass();
        }
        else {
            return clazz;
        }
    }

    @Nullable
    public boolean isConfigurationSource(BeanDefinition beanDefinition) {
        if (beanDefinition == null) return false;
        return beanDefinition
                .getClass()
                .getCanonicalName()
                .startsWith(
                        "org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader");
    }
}
