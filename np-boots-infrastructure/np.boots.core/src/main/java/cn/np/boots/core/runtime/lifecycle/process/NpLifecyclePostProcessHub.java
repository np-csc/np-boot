package cn.np.boots.core.runtime.lifecycle.process;

import cn.np.boots.core.runtime.lifecycle.NpLifecycleHub;
import cn.np.boots.core.runtime.lifecycle.callback.NpLifecycleCallbackHub;

public class NpLifecyclePostProcessHub {
    private final NpLifecycleHub hub;
    private final NpLifecycleCallbackHub callbackHub;
    private final NpSpringOnBeanDefinitionPostProcessor beanDefinitionPostProcessor = new NpSpringOnBeanDefinitionPostProcessor();
    private final NpSpringOnBeanFactoryPostProcessor beanFactoryPostProcessor = new NpSpringOnBeanFactoryPostProcessor();
    private final NpSpringOnBeanPostProcessor beanPostProcessor = new NpSpringOnBeanPostProcessor();
    private final NpSpringOnApplicationStartedPostProcessor applicationStartedPostProcessor = new NpSpringOnApplicationStartedPostProcessor();

    public NpLifecyclePostProcessHub(NpLifecycleHub hub) {
        this.hub = hub;
        this.callbackHub = hub.getCallbackHub();
    }


}
