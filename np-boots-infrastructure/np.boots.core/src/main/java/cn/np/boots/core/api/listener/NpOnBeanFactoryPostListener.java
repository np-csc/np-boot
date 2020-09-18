package cn.np.boots.core.api.listener;


public interface NpOnBeanFactoryPostListener {
    default void onBeanFactoryPost(NpOnBeanFactoryPostEvent event) {}
}
