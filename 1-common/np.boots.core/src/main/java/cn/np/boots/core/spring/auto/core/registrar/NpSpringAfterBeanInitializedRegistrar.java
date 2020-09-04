package cn.np.boots.core.spring.auto.core.registrar;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NpSpringAfterBeanInitializedRegistrar implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        return null;
    }
}
