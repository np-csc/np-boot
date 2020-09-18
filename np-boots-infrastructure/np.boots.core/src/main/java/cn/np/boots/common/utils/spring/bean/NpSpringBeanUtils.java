package cn.np.boots.common.utils.spring.bean;

import cn.np.boots.common.utils.NpUtils;
import cn.np.boots.common.pattern.asserts.AssertPattern;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.stereotype.Component;

@Getter
@Accessors(fluent = true)
@Component
public class NpSpringBeanUtils {

    public <T> T tryGetBeanByType(Class<T> beanClass) {
        try {
            return NpUtils.spring().context().getBeanFactory().getBean(beanClass);
        } catch (NoSuchBeanDefinitionException e) {
            return null;
        }
    }

    public <T> T tryGetBeanByName(String beanName, Class<T> beanClass) {
        try {
            return NpUtils.spring().context().getBeanFactory().getBean(beanName, beanClass);
        } catch (NoSuchBeanDefinitionException e) {
            return null;
        }
    }

    public <T> T getOrRegisterSingleBeanByName(String beanName, Class<T> beanClass, Object... args) {
        T bean = this.tryGetBeanByName(beanName, beanClass);
        if (bean != null) return bean;

        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                beanDefinitionBuilder.addConstructorArgValue(arg);
            }
        }
        beanDefinitionBuilder.setLazyInit(true);
        beanDefinitionBuilder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
//        beanDefinitionBuilder.setDependencyCheck(AbstractBeanDefinition.DEPENDENCY_CHECK_ALL);


        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        DefaultListableBeanFactory beanFactory = NpUtils.spring().context().getBeanFactory();
        beanFactory.registerBeanDefinition(beanName, beanDefinition);

        Object object = AssertPattern.notNull(this.tryGetBeanByName(beanName, beanClass), "getOrRegisterSingleBeanByName failed");

        ConfigurationPropertiesBindingPostProcessor configurationPropertiesBindingPostProcessor =
                beanFactory.getBean(ConfigurationPropertiesBindingPostProcessor.class);
        configurationPropertiesBindingPostProcessor.postProcessBeforeInitialization(object, beanName);

        AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = beanFactory.getBean(AutowiredAnnotationBeanPostProcessor.class);
        autowiredAnnotationBeanPostProcessor.postProcessBeforeInitialization(object, beanName);

        return (T) object;
    }

    public <T> T registerSingleBeanByName(String beanName, T bean) {
        DefaultListableBeanFactory beanFactory = NpUtils.spring().context().getBeanFactory();
        beanFactory.registerSingleton(beanName, bean);
        return AssertPattern.notNull((T) this.tryGetBeanByName(beanName, bean.getClass()), "registerSingleBeanByName failed");
    }

    public void registerBeanDefinitionByName(String beanName, BeanDefinition beanDefinition) {
        DefaultListableBeanFactory beanFactory = NpUtils.spring().context().getBeanFactory();
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

//    public <T>  T getOrAutowiredBean(Class<T> beanClass){
//        T bean = this.tryGetBeanByType(beanClass);
//        if(bean != null) return bean;
//
//        ConfigurableApplicationContext applicationContext = SpringFacade.context.getApplicationContext();
//        AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
//        bean = (T)beanFactory.autowire(beanClass,AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE,false);
//
//       boolean is = beanFactory.containsBean(beanClass.getName());
//
//
//        return bean;
//    }
}
