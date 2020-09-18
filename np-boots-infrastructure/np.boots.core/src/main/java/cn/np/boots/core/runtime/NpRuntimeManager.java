package cn.np.boots.core.runtime;

import cn.np.boots.core.runtime.component.NpComponentManager;
import cn.np.boots.core.runtime.context.NpRuntimeContext;
import cn.np.boots.core.runtime.spi.NpSpiServiceLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

public interface NpRuntimeManager {
    String appName();

    NpRuntimeContext getRuntimeContext();

    ConfigurableApplicationContext getApplicationContext();

    ApplicationContext getRootApplicationContext();

    NpSpiServiceLoader serviceLoader();

    default ClassLoader getAppClassLoader() {
        return this.getRuntimeContext().getClassLoader();
    }

    default NpComponentManager getComponentManager() {
        return this.getRuntimeContext().getComponentManager();
    }

    void start();

    void shutdown();
}
