package cn.np.boots.core.api.listener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

@Getter
@AllArgsConstructor
public class NpOnBeanFactoryPostEvent {
    private ConfigurableListableBeanFactory beanFactory;
}
