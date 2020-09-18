package cn.np.boots.core.runtime.lifecycle.process;

import cn.np.boots.common.pattern.NpPattern;
import cn.np.boots.core.api.listener.NpOnBeanFactoryPostEvent;
import cn.np.boots.core.runtime.lifecycle.callback.NpLifecycleCallbackHub;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class NpSpringOnBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    private final NpLifecycleCallbackHub callbackHub = NpLifecycleCallbackHub.getInstance();
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        NpPattern.log().aroundDebug("NpSpringOnBeanFactoryPostProcessor",()->{
            callbackHub.notifyOnBeanFactoryPostCallback(new NpOnBeanFactoryPostEvent(beanFactory));
        });
    }
}
