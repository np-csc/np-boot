package cn.np.boots.core.spring.auto.configurer;

import lombok.Data;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.type.AnnotationMetadata;

@Data
public abstract class NpSpringAutoAfterApplicationStartedConfigurer{
    protected MergedAnnotation<?> annotation;
    protected AnnotationMetadata annotationMetadata;
    public abstract void doAction();
}
