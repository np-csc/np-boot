package cn.np.boots.core.runtime;

import cn.np.boots.core.runtime.component.NpComponentManager;
import cn.np.boots.core.runtime.context.NpRuntimeContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class NpStandardRuntimeManager implements NpRuntimeManager, ApplicationContextAware {

    private final String appName;
    private final ClassLoader appClassLoader;
    private final NpRuntimeContext runtimeContext;
    private final NpComponentManager componentManager;
    private ApplicationContext rootApplicationContext;

    public NpStandardRuntimeManager(String appName, ClassLoader appClassLoader){
        this.appName = appName;
        this.appClassLoader = appClassLoader;
        this.componentManager = null;
        this.runtimeContext = new NpRuntimeContext(this,componentManager,appClassLoader);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.rootApplicationContext = applicationContext;
    }

    @Override
    public String appName() {
        return this.appName;
    }

    @Override
    public NpRuntimeContext getRuntimeContext() {
        return this.runtimeContext;
    }

    @Override
    public ApplicationContext getRootApplicationContext() {
        return this.rootApplicationContext;
    }

    @Override
    public void start() {

    }

    @Override
    public void shutdown() {

    }
}
