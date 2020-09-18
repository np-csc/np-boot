package cn.np.boots.core.api.listener;

public interface NpOnApplicationStartedListener {
    default void onApplicationStarted(NpOnApplicationStartedEvent event) {
    }
}
