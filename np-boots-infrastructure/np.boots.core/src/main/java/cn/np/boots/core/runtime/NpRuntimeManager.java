package cn.np.boots.core.runtime;

import cn.np.boots.core.runtime.component.NpComponentManager;
import cn.np.boots.core.runtime.context.NpRuntimeContext;
import org.springframework.context.ApplicationContext;

public interface NpRuntimeManager {
    String appName();

    NpRuntimeContext getRuntimeContext();

    ApplicationContext getRootApplicationContext();

    default ClassLoader getAppClassLoader() {
        return this.getRuntimeContext().getClassLoader();
    }

    default NpComponentManager getComponentManager() {
        return this.getRuntimeContext().getComponentManager();
    }

    void start();

    void shutdown();
}
