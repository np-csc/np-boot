package cn.np.boots.core.runtime.lifecycle.callback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.Annotation;

@Getter
@Builder
public class NpLifecycleCallback<T> implements Comparable<NpLifecycleCallback> {
    private Integer order = 0;
    private AnnotationMetadata annotationMetadata;
    private BeanDefinitionRegistry registry;
    private BeanNameGenerator importBeanNameGenerator;
    private Class<?> callbackActionBeanClass;
    private String callbackActionBeanName;
    private T callbackBean;
    private MergedAnnotation<?> annotation;
    private Class<? extends Annotation> annotationClass;

    @Override
    public int compareTo(@NotNull NpLifecycleCallback o) {
        return this.order.compareTo(o.order);
    }
}
