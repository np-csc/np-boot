package cn.np.boots.core.runtime.lifecycle.callback;

import cn.np.boots.core.api.listener.*;
import lombok.Getter;

import java.util.TreeSet;

@Getter
public class NpLifecycleCallbackHub {
    private static final NpLifecycleCallbackHub instance = new NpLifecycleCallbackHub();

    public static final NpLifecycleCallbackHub getInstance() {
        return instance;
    }

    private TreeSet<NpLifecycleCallback<? extends NpOnApplicationStartedListener>> onApplicationStartedCallback = new TreeSet<>();
    private TreeSet<NpLifecycleCallback<? extends NpOnBeanDefinitionListener>> onBeanDefinitionCallback = new TreeSet<>();
    private TreeSet<NpLifecycleCallback<? extends NpOnBeanFactoryPostListener>> onBeanFactoryPostCallback = new TreeSet<>();
    private TreeSet<NpLifecycleCallback<? extends NpOnBeanPostListener>> onBeanPostCallback = new TreeSet<>();


    public NpLifecycleCallbackHub registerOnApplicationStartedCallback(NpLifecycleCallback<? extends NpOnApplicationStartedListener> callback) {
        this.onApplicationStartedCallback.add(callback);
        return this;
    }

    public NpLifecycleCallbackHub notifyOnApplicationStartedCallback(NpOnApplicationStartedEvent event) {
        this.getOnApplicationStartedCallback().forEach(callback -> {
            callback.getCallbackBean().onApplicationStarted(event);
        });
        return this;
    }


    public NpLifecycleCallbackHub registerOnBeanDefinitionCallback(NpLifecycleCallback<? extends NpOnBeanDefinitionListener> callback) {
        this.onBeanDefinitionCallback.add(callback);
        return this;
    }

    public NpLifecycleCallbackHub notifyOnBeanDefinitionCallback(NpOnBeanDefinitionEvent event) {
        this.getOnBeanDefinitionCallback().forEach(callback->{
            callback.getCallbackBean().onBeanDefinition(event);
        });
        return this;
    }



    public NpLifecycleCallbackHub registerOnBeanFactoryPostCallback(NpLifecycleCallback<? extends NpOnBeanFactoryPostListener> callback) {
        this.onBeanFactoryPostCallback.add(callback);
        return this;
    }

    public NpLifecycleCallbackHub notifyOnBeanFactoryPostCallback(NpOnBeanFactoryPostEvent event) {
        this.getOnBeanFactoryPostCallback().forEach(callback->{
            callback.getCallbackBean().onBeanFactoryPost(event);
        });
        return this;
    }

    public NpLifecycleCallbackHub registerOnBeanPostCallback(NpLifecycleCallback<? extends NpOnBeanPostListener> callback) {
        this.onBeanPostCallback.add(callback);
        return this;
    }

    public NpLifecycleCallbackHub notifyOnBeanPostCallback(NpOnBeanPostEvent event) {
        this.getOnBeanPostCallback().forEach(callback->{
            callback.getCallbackBean().onBeanPost(event);
        });
        return this;
    }
}
