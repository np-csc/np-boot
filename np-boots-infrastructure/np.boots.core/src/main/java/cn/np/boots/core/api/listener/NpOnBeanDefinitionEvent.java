package cn.np.boots.core.api.listener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.core.type.AnnotationMetadata;

@Getter
@AllArgsConstructor
public class NpOnBeanDefinitionEvent {
   private AnnotationMetadata annotationMetadata;
   private BeanDefinitionRegistry registry;
   private BeanNameGenerator importBeanNameGenerator;
}
