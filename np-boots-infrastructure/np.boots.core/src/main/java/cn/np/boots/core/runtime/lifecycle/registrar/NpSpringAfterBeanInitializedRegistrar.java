package cn.np.boots.core.runtime.lifecycle.registrar;

import cn.np.boots.common.pattern.NpPattern;
import cn.np.boots.core.api.listener.NpOnBeanPostEvent;
import cn.np.boots.core.runtime.lifecycle.callback.NpLifecycleCallbackHub;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NpSpringAfterBeanInitializedRegistrar implements BeanPostProcessor {

    private final NpLifecycleCallbackHub callbackHub = NpLifecycleCallbackHub.getInstance();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        NpPattern.log().aroundDebug("",()->callbackHub.notifyOnBeanPostCallback(new NpOnBeanPostEvent(bean, beanName)));
        return bean;
    }
}
