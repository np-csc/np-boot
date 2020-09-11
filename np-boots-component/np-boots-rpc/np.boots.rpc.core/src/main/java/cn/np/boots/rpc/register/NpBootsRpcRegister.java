package cn.np.boots.rpc.register;

import cn.np.boots.common.utils.NpUtils;
import cn.np.boots.rpc.annotation.NpServiceProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.stream.Stream;

@Slf4j
public class NpBootsRpcRegister implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Stream.of(beanFactory.getBeanDefinitionNames())
            .forEach(beanDefinitionName->{
                this.processBean(beanDefinitionName,beanFactory.getBeanDefinition(beanDefinitionName),beanFactory);
            });
    }

    protected void processBean(String beanDefinitionName, BeanDefinition beanDefinition,ConfigurableListableBeanFactory beanFactory){

    }

    protected void processRpcBean(String beanDefinitionName, BeanDefinition beanDefinition,ConfigurableListableBeanFactory beanFactory){
        if(NpUtils.spring().beanDefinition().isConfigurationSource(beanDefinition)){

        }
        else {
            Class<?> beanType = NpUtils.spring().beanDefinition().resolveBeanClassType(beanDefinition);
            if(beanType == null){
                log.error("resolve bean error:{}",beanDefinitionName);
                return;
            }
            NpServiceProvider npServiceProvider = beanType.getAnnotation(NpServiceProvider.class);

        }
    }
}
