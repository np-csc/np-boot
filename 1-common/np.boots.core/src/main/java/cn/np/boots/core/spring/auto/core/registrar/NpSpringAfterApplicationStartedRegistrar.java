package cn.np.boots.core.spring.auto.core.registrar;

import cn.np.boots.common.utils.NpUtils;
import cn.np.boots.core.pattern.asserts.AssertPattern;
import cn.np.boots.core.spring.auto.configurer.NpSpringAutoAfterApplicationStartedConfigurer;
import cn.np.boots.core.spring.auto.core.NpSpringAutoContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import java.util.List;

@Slf4j
public class NpSpringAfterApplicationStartedRegistrar implements ApplicationListener<ApplicationStartedEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        if (log.isDebugEnabled()) {
            log.debug("Np:NpSpringAfterApplicationStartedAutoRegistrar begin");
        }

        List<Class<? extends NpSpringAutoAfterApplicationStartedConfigurer>> actionClasses = NpSpringAutoContext.afterApplicationStarted();
        actionClasses.forEach(actionClass -> {
            NpSpringAutoAfterApplicationStartedConfigurer autoAfterApplicationStartedAction = AssertPattern.notNull(NpUtils.spring().bean().tryGetBeanByType(actionClass), "{} load failed", actionClass.toString());
            autoAfterApplicationStartedAction.doAction();
        });

        if (log.isDebugEnabled()) {
            log.debug("Np:NpSpringAfterApplicationStartedAutoRegistrar end");
        }
    }
}
