package cn.np.boots.core.spring.auto.core;

import cn.np.boots.common.utils.NpUtils;
import cn.np.boots.core.pattern.asserts.AssertPattern;
import cn.np.boots.core.spring.auto.*;
import cn.np.boots.core.spring.auto.configurer.NpSpringAutoAfterApplicationStartedConfigurer;
import cn.np.boots.core.spring.auto.configurer.NpSpringAutoAfterBeanInitializedConfigurer;
import cn.np.boots.core.spring.auto.configurer.NpSpringAutoBeforeBeanDefinitionConfigurer;
import cn.np.boots.core.spring.auto.core.registrar.NpSpringBeforeBeanDefinitionRegistrar;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.Annotation;
import java.util.TreeSet;
import java.util.stream.Stream;

@Slf4j
public class NpSpringAutoRegistrar implements ImportBeanDefinitionRegistrar, BeanFactoryAware {

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
        ConfigurationPropertiesBindingPostProcessor.register(registry);

        if (log.isDebugEnabled()) {
            log.debug("Np:NpSpringAutoRegistrar scan begin");
        }

        NpSpringAutoRegistrarProcessor[] processors = {
                new NpAutoBeforeBeanDefinitionRegistrarProcessor(EnableNpAutoBeforeBeanDefinition.class, EnableNpAutoBeforeBeanDefinitionRepeatable.class),
                new NpAutoAfterBeanInitializedRegistrarProcessor(EnableNpAutoAfterBeanInitialized.class, EnableNpAutoAfterBeanInitializedRepeatable.class),
                new NpAutoAfterApplicationStartedRegistrarProcessor(EnableNpAutoAfterApplicationStarted.class, EnableNpAutoAfterApplicationStartedRepeatable.class)
        };

        Stream.of(processors).forEach(processor -> {
            processor.scan(annotationMetadata).process(registry, importBeanNameGenerator);
        });

        NpSpringBeforeBeanDefinitionRegistrar.registerBeanDefinitions(annotationMetadata, registry, importBeanNameGenerator);
    }


    @Getter
    private class NpSpringAutoActionItem implements Comparable<NpSpringAutoActionItem> {
        private Integer order;
        private MergedAnnotation<? extends Annotation> annotation;

        public NpSpringAutoActionItem(Integer order, MergedAnnotation<? extends Annotation> annotation) {
            this.order = order;
            this.annotation = annotation;
        }

        @Override
        public int compareTo(@NotNull NpSpringAutoActionItem o) {
            return this.order.compareTo(o.order);
        }
    }

    private abstract class NpSpringAutoRegistrarProcessor {
        protected AnnotationMetadata annotationMetadata;
        protected Class<? extends Annotation> annotationClass;
        protected Class<? extends Annotation> annotationRepeatableClass;
        protected TreeSet<NpSpringAutoActionItem> actions = new TreeSet<>();

        public NpSpringAutoRegistrarProcessor(Class<? extends Annotation> annotationClass,
                                              Class<? extends Annotation> annotationRepeatableClass) {
            this.annotationClass = annotationClass;
            this.annotationRepeatableClass = annotationRepeatableClass;
        }

        public NpSpringAutoRegistrarProcessor scan(AnnotationMetadata annotationMetadata) {
            this.annotationMetadata = annotationMetadata;
            annotationMetadata.getAnnotations().stream(this.annotationClass).forEach(annotation -> {
                int order = annotation.getInt(NpSpringConstants.PROPERTY_ORDER);
                this.actions.add(new NpSpringAutoActionItem(order, annotation));
            });
            annotationMetadata.getAnnotations().stream(this.annotationRepeatableClass).forEach(annotationRepeatable -> {
                MergedAnnotation<?>[] annotations = annotationRepeatable.getAnnotationArray(NpSpringConstants.REPEATABLE_PROPERTY_VALUE, this.annotationClass);
                Stream.of(annotations).forEach(annotation -> {
                    int order = annotation.getInt(NpSpringConstants.PROPERTY_ORDER);
                    this.actions.add(new NpSpringAutoActionItem(order, annotation));
                });
            });
            return this;
        }

        public NpSpringAutoRegistrarProcessor process(BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
            this.actions.forEach(action -> this.doProcess(action.annotation, registry, importBeanNameGenerator));
            return this;
        }

        protected abstract void doProcess(MergedAnnotation<?> annotation, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator);
    }

    private class NpAutoBeforeBeanDefinitionRegistrarProcessor extends NpSpringAutoRegistrarProcessor {
        public NpAutoBeforeBeanDefinitionRegistrarProcessor(Class<? extends Annotation> annotationClass,
                                                            Class<? extends Annotation> annotationRepeatableClass) {
            super(annotationClass, annotationRepeatableClass);
        }

        @Override
        public void doProcess(MergedAnnotation<?> annotation, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
            Class<NpSpringAutoBeforeBeanDefinitionConfigurer> actionClass =
                    AssertPattern.notNull((Class<NpSpringAutoBeforeBeanDefinitionConfigurer>) annotation.getClass(NpSpringConstants.PROPERTY_ACTION),
                            "EnableNpAutoBeforeBeanDefinition: {0} can not be null", NpSpringConstants.PROPERTY_ACTION);
            String actionBeanName = NpUtils.value().getOrElse(annotation.getString(NpSpringConstants.PROPERTY_ACTION_BEAN_NAME), actionClass.toString());
            Object obj = NpUtils.spring().bean().tryGetBeanByType(actionClass);
            if (obj != null) return;
            BeanDefinitionBuilder actionBeanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(actionClass);
            actionBeanDefinitionBuilder
                    .addPropertyValue("annotation", annotation)
                    .addPropertyValue("registry", registry)
                    .addPropertyValue("importBeanNameGenerator", importBeanNameGenerator);
            BeanDefinition actionBeanDefinition = actionBeanDefinitionBuilder.getBeanDefinition();
            NpUtils.spring().bean().registerBeanDefinitionByName(actionBeanName, actionBeanDefinition);
            AssertPattern.notNull(NpUtils.spring().bean().tryGetBeanByType(actionClass), "Np:NpAutoBeforeBeanDefinitionRegistrarProcessor register {} failed", actionClass, toString());

            NpSpringAutoContext.registerBeforeBeanDefinitionAction(actionClass);
        }
    }

    private class NpAutoAfterBeanInitializedRegistrarProcessor extends NpSpringAutoRegistrarProcessor {
        public NpAutoAfterBeanInitializedRegistrarProcessor(Class<? extends Annotation> annotationClass,
                                                            Class<? extends Annotation> annotationRepeatableClass) {
            super(annotationClass, annotationRepeatableClass);
        }

        @Override
        public void doProcess(MergedAnnotation<?> annotation, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
            Class<NpSpringAutoAfterBeanInitializedConfigurer> actionClass =
                    AssertPattern.notNull((Class<NpSpringAutoAfterBeanInitializedConfigurer>) annotation.getClass(NpSpringConstants.PROPERTY_ACTION),
                            "EnableNpAutoBeforeBeanDefinition: {0} can not be null", NpSpringConstants.PROPERTY_ACTION);
            String actionBeanName = NpUtils.value().getOrElse(annotation.getString(NpSpringConstants.PROPERTY_ACTION_BEAN_NAME), actionClass.toString());

            Object obj = NpUtils.spring().bean().tryGetBeanByType(actionClass);
            if (obj != null) return;

            BeanDefinitionBuilder actionBeanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(actionClass);
            actionBeanDefinitionBuilder
                    .addPropertyValue("annotation", annotation);

            BeanDefinition actionBeanDefinition = actionBeanDefinitionBuilder.getBeanDefinition();
            NpUtils.spring().bean().registerBeanDefinitionByName(actionBeanName, actionBeanDefinition);
            AssertPattern.notNull(NpUtils.spring().bean().tryGetBeanByType(actionClass), "Np:NpAutoAfterBeanInitializedRegistrarProcessor register {} failed", actionClass, toString());


            NpSpringAutoContext.afterBeanInitialized(actionClass);
        }
    }

    private class NpAutoAfterApplicationStartedRegistrarProcessor extends NpSpringAutoRegistrarProcessor {
        public NpAutoAfterApplicationStartedRegistrarProcessor(Class<? extends Annotation> annotationClass,
                                                               Class<? extends Annotation> annotationRepeatableClass) {
            super(annotationClass, annotationRepeatableClass);
        }

        @Override
        public void doProcess(MergedAnnotation<?> annotation, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
            Class<NpSpringAutoAfterApplicationStartedConfigurer> actionClass =
                    AssertPattern.notNull((Class<NpSpringAutoAfterApplicationStartedConfigurer>) annotation.getClass(NpSpringConstants.PROPERTY_ACTION),
                            "EnableNpAutoBeforeBeanDefinition: {0} can not be null", NpSpringConstants.PROPERTY_ACTION);
            String actionBeanName = NpUtils.value().getOrElse(annotation.getString(NpSpringConstants.PROPERTY_ACTION_BEAN_NAME), actionClass.getName());
            Object obj = NpUtils.spring().bean().tryGetBeanByType(actionClass);

            if (obj != null) return;

            BeanDefinitionBuilder actionBeanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(actionClass);
            actionBeanDefinitionBuilder
                    .addPropertyValue("annotation", annotation)
                    .addPropertyValue("annotationMetadata", this.annotationMetadata);

            BeanDefinition actionBeanDefinition = actionBeanDefinitionBuilder.getBeanDefinition();

            NpUtils.spring().bean().registerBeanDefinitionByName(actionBeanName, actionBeanDefinition);
            AssertPattern.notNull(NpUtils.spring().bean().tryGetBeanByType(actionClass), "Np:NpAutoAfterApplicationStartedRegistrarProcessor register {} failed", actionClass, toString());

            NpSpringAutoContext.afterApplicationStarted(actionClass);
        }
    }
}
