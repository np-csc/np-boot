package cn.np.boots.core.api.listener;

public interface NpOnBeanPostListener {
    default void onBeanPost(NpOnBeanPostEvent event) {}
}
