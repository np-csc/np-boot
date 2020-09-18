package cn.np.boots.core.runtime.lifecycle;

import cn.np.boots.common.pattern.NpPattern;
import cn.np.boots.core.api.listener.EnableNpBootsListener;
import cn.np.boots.core.api.listener.EnableNpBootsListenerRepeatable;
import cn.np.boots.core.api.listener.NpBootsLifecycleListener;
import cn.np.boots.core.runtime.lifecycle.callback.NpLifecycleCallback;
import cn.np.boots.core.runtime.lifecycle.callback.NpLifecycleCallbackHub;
import cn.np.boots.core.runtime.lifecycle.registrar.NpSpringBeforeBeanDefinitionRegistrar;
import cn.np.boots.core.spring.auto.core.NpSpringConstants;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.*;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;

@Slf4j
@Getter
public class NpLifecycleHub implements ImportBeanDefinitionRegistrar, BeanFactoryAware {
    private DefaultListableBeanFactory beanFactory;
    private ConfigurationPropertiesBindingPostProcessor configurationPropertiesBindingPostProcessor;
    private AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor;

    private NameGenerator nameGenerator = new NameGenerator();
    private NpLifecycleCallbackHub callbackHub = NpLifecycleCallbackHub.getInstance();

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory)beanFactory;
        configurationPropertiesBindingPostProcessor = beanFactory.getBean(ConfigurationPropertiesBindingPostProcessor.class);
        autowiredAnnotationBeanPostProcessor = beanFactory.getBean(AutowiredAnnotationBeanPostProcessor.class);
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
        ConfigurationPropertiesBindingPostProcessor.register(registry);

        if (log.isDebugEnabled()) {
            log.debug("Np:NpSpringAutoRegistrar scan begin");
        }

        NpAnnotationScanner[] scanners = {
                new NpAnnotationScanner(EnableNpBootsListener.class, EnableNpBootsListenerRepeatable.class) {
                    @Override
                    protected void process(MergedAnnotation annotation, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator, AnnotationMetadata annotationMetadata) {
                        Class<NpBootsLifecycleListener> listenerClass = (Class<NpBootsLifecycleListener>) annotation.getClass(EnableNpBootsListener.PROPERTY_LIFECYCLE);
                        String listenerBeanName = NpPattern.get().getStringOrEmptyElse(EnableNpBootsListener.PROPERTY_Name, nameGenerator.generate(listenerClass));

                        Object listenerBean = null;
                        if(!beanFactory.containsBean(listenerBeanName)){
                            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(listenerClass);
//                            beanDefinitionBuilder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
                            BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
                            beanFactory.registerBeanDefinition(listenerBeanName,beanDefinition);
                            listenerBean = beanFactory.getBean(listenerBeanName);
                            configurationPropertiesBindingPostProcessor.postProcessBeforeInitialization(listenerBean, listenerBeanName);
                            autowiredAnnotationBeanPostProcessor.postProcessBeforeInitialization(listenerBean, listenerBeanName);
                        }
                        else {
                            listenerBean = beanFactory.getBean(listenerBeanName);
                        }



                        NpLifecycleCallback callback = NpLifecycleCallback.builder()
                                .order(annotation.getInt(EnableNpBootsListener.PROPERTY_ORDER))
                                .annotationMetadata(annotationMetadata)
                                .registry(registry)
                                .importBeanNameGenerator(importBeanNameGenerator)
                                .annotation(annotation)
                                .annotationClass(EnableNpBootsListener.class)
                                .callbackActionBeanClass(listenerClass)
                                .callbackActionBeanName(listenerBeanName)
                                .callbackBean(listenerBean)
                                .build();
                        callbackHub.registerOnBeanDefinitionCallback(callback)
                                .registerOnBeanFactoryPostCallback(callback)
                                .registerOnBeanPostCallback(callback)
                                .registerOnApplicationStartedCallback(callback);
                    }
                }
        };

        Stream.of(scanners).forEach(processor -> {
            processor.scan(annotationMetadata, registry, importBeanNameGenerator);
        });

        callbackHub.getOnBeanDefinitionCallback().forEach(callback -> {

        });
        NpSpringBeforeBeanDefinitionRegistrar.registerBeanDefinitions(annotationMetadata, registry, importBeanNameGenerator);
    }


    private abstract class NpAnnotationScanner<T extends Annotation, TRepeatable extends Annotation> {
        protected Class<T> annotationClass;
        protected Class<TRepeatable> annotationRepeatableClass;

        public NpAnnotationScanner(Class<T> annotationClass, Class<TRepeatable> annotationRepeatableClass) {
            this.annotationClass = annotationClass;
            this.annotationRepeatableClass = annotationRepeatableClass;
        }

        public NpAnnotationScanner scan(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
            annotationMetadata.getAnnotations().stream(this.annotationClass).forEach(annotation -> {
                this.process(annotation, registry, importBeanNameGenerator, annotationMetadata);
            });
            annotationMetadata.getAnnotations().stream(this.annotationRepeatableClass).forEach(annotationRepeatable -> {
                MergedAnnotation<?>[] annotations = annotationRepeatable.getAnnotationArray(NpSpringConstants.REPEATABLE_PROPERTY_VALUE, this.annotationClass);
                Stream.of(annotations).forEach(annotation -> {
                    this.process(annotation, registry, importBeanNameGenerator, annotationMetadata);
                });
            });
            return this;
        }

        protected abstract void process(MergedAnnotation<?> annotation, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator, AnnotationMetadata annotationMetadata);
    }

    private class NameGenerator {
        public String generate(Class<?> clazz) {
            return "NpLifecycleBean#" + clazz.toString();
        }
    }
}
