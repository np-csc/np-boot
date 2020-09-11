package cn.np.boots.common.utils.spring.context;

import cn.np.boots.core.pattern.asserts.AssertPattern;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class NpSpringApplicationContextUtils implements ApplicationContextInitializer {

    private static ConfigurableApplicationContext applicationContext;
    private static DefaultListableBeanFactory beanFactory;

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        NpSpringApplicationContextUtils.applicationContext = configurableApplicationContext;
        NpSpringApplicationContextUtils.beanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
    }

    public ConfigurableApplicationContext getApplicationContext() {
        return NpSpringApplicationContextUtils.getStaticApplicationContext();
    }

    public DefaultListableBeanFactory getBeanFactory(){
        return NpSpringApplicationContextUtils.getStaticBeanFactory();
    }

    public static ConfigurableApplicationContext getStaticApplicationContext() {
        AssertPattern.notNull(NpSpringApplicationContextUtils.applicationContext, "SpringApplicationContext can not be null");
        return NpSpringApplicationContextUtils.applicationContext;
    }

    public static DefaultListableBeanFactory getStaticBeanFactory(){
        AssertPattern.notNull(NpSpringApplicationContextUtils.beanFactory, "SpringApplicationContext-BeanFactory can not be null");
        return NpSpringApplicationContextUtils.beanFactory;
    }
}
