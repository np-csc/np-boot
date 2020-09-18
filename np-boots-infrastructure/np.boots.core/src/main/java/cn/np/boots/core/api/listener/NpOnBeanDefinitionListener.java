package cn.np.boots.core.api.listener;

public interface NpOnBeanDefinitionListener {
    default void onBeanDefinition(NpOnBeanDefinitionEvent event) {
    }
}
