package cn.np.boots.rpc.core.register;

import cn.np.boots.common.utils.NpUtils;
import cn.np.boots.core.api.aware.NpRuntimeAware;
import cn.np.boots.core.api.listener.NpBootsLifecycleListener;
import cn.np.boots.core.api.listener.NpOnBeanDefinitionEvent;
import cn.np.boots.core.api.listener.NpOnBeanFactoryPostEvent;
import cn.np.boots.core.api.listener.NpOnBeanPostEvent;
import cn.np.boots.core.runtime.NpRuntimeManager;
import cn.np.boots.core.runtime.spi.NpSpiServiceLoader;
import cn.np.boots.rpc.common.ServiceBeanNameGenerator;
import cn.np.boots.rpc.core.service.NpServiceBean;
import cn.np.boots.rpc.core.service.NpServiceProviderBean;
import cn.np.boots.rpc.exception.NpRpcException;
import cn.np.boots.rpc.provider.NpServiceProvider;
import cn.np.boots.rpc.provider.spi.INpServiceProviderSPI;
import cn.np.boots.rpc.provider.spi.NpServiceProviderInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Set;
import java.util.stream.Stream;

@Slf4j
public class NpRpcRegister implements NpBootsLifecycleListener, NpRuntimeAware {
    private NpRuntimeManager runtimeManager;
    private NpSpiServiceLoader serviceLoader;
    private ServiceBeanNameGenerator serviceBeanNameGenerator = new ServiceBeanNameGenerator();

    @Override
    public void setNpRuntimeManager(NpRuntimeManager runtimeManager) {
        this.runtimeManager = runtimeManager;
        this.serviceLoader = runtimeManager.serviceLoader();
    }

    @Override
    public void onBeanDefinition(NpOnBeanDefinitionEvent event) {

    }

    @Override
    public void onBeanPost(NpOnBeanPostEvent event) {
        Object bean = event.getBean();
        Class<?> beanType = bean.getClass();
        NpServiceProvider npServiceProvider = AnnotationUtils.getAnnotation(bean.getClass(), NpServiceProvider.class);
        if (npServiceProvider == null) return;

        String serviceUniqueId = npServiceProvider.serviceUniqueId();
        Class<?> serviceContractClazz = npServiceProvider.contract();

        if (Void.class.equals(serviceContractClazz) || serviceContractClazz == null) {
            serviceContractClazz = beanType;
        }

        NpServiceProviderInfo serviceProviderInfo = new NpServiceProviderInfo();
        serviceProviderInfo.setAddress(npServiceProvider.address());
        serviceProviderInfo.setBinding(serviceProviderInfo.getBinding());
        serviceProviderInfo.setContractClass(serviceContractClazz);
        serviceProviderInfo.setContractInstance(bean);
        serviceProviderInfo.setServiceUniqueId(serviceUniqueId);

        Set<INpServiceProviderSPI> serviceProviderSPIS = this.serviceLoader.load(INpServiceProviderSPI.class);
        if (serviceProviderSPIS == null || serviceProviderSPIS.size() == 0) {
            throw new NpRpcException("Unknow np-rpc's provider spi");
        }

        serviceProviderSPIS.forEach(x -> {
            x.register(this.runtimeManager, serviceProviderInfo);
        });
    }

    @Override
    public void onBeanFactoryPost(NpOnBeanFactoryPostEvent event) {
        final ConfigurableListableBeanFactory beanFactory = event.getBeanFactory();
        Stream.of(beanFactory.getBeanDefinitionNames())
                .forEach(beanDefinitionName -> {
                    this.processRpcBean(beanDefinitionName, beanFactory.getBeanDefinition(beanDefinitionName), beanFactory);
                });
    }

    protected void processRpcBean(String beanDefinitionName, BeanDefinition beanDefinition,
                                  ConfigurableListableBeanFactory beanFactory) {
        if (NpUtils.spring().beanDefinition().isConfigurationSource(beanDefinition)) {

        } else {
            Class<?> beanType = NpUtils.spring().beanDefinition().resolveBeanClassType(beanDefinition);
            if (beanType == null) {
                log.error("resolve bean error:{}", beanDefinitionName);
                return;
            }
            NpServiceProvider npServiceProvider = beanType.getAnnotation(NpServiceProvider.class);


            String beanName = serviceBeanNameGenerator.generateServiceBeanName(serviceContractClazz, serviceUniqueId);

            if (beanFactory.containsBeanDefinition(beanName)) {
                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition();
                beanDefinitionBuilder.addDependsOn(beanDefinitionName);
                beanDefinitionBuilder.getRawBeanDefinition().setScope(beanDefinition.getScope());
                beanDefinitionBuilder.setLazyInit(beanDefinition.isLazyInit());
                beanDefinitionBuilder.getRawBeanDefinition().setBeanClass(NpServiceProviderBean.class);

                beanDefinitionBuilder.addPropertyValue(NpServiceBean.PROPERTY_SERVICE_CONTRACT_TYPE, serviceContractClazz);
                beanDefinitionBuilder.addPropertyValue(NpServiceBean.PROPERTY_SERVICE_UNIQUE_ID, serviceUniqueId);
            }
        }
    }
}
