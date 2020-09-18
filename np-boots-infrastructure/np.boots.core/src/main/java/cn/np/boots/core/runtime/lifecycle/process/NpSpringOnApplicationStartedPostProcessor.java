package cn.np.boots.core.runtime.lifecycle.process;

import cn.np.boots.common.pattern.NpPattern;
import cn.np.boots.core.api.listener.NpOnApplicationStartedEvent;
import cn.np.boots.core.api.listener.NpOnBeanFactoryPostEvent;
import cn.np.boots.core.runtime.lifecycle.callback.NpLifecycleCallbackHub;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

@Slf4j
public class NpSpringOnApplicationStartedPostProcessor implements ApplicationListener<ApplicationStartedEvent> {

    private final NpLifecycleCallbackHub callbackHub = NpLifecycleCallbackHub.getInstance();

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        NpPattern.log().aroundDebug("NpSpringOnApplicationStartedPostProcessor",()->{
            callbackHub.notifyOnApplicationStartedCallback(new NpOnApplicationStartedEvent(event));
        });
    }
}
