package cn.np.boots.core.runtime.context;

import cn.np.boots.core.runtime.NpRuntimeManager;
import cn.np.boots.core.runtime.component.NpComponentManager;
import lombok.Getter;

@Getter
public class NpRuntimeContext {
    private final NpRuntimeManager runtimeManager;
    private final NpComponentManager componentManager;
    private final ClassLoader classLoader;

    public NpRuntimeContext(NpRuntimeManager runtimeManager,NpComponentManager componentManager,ClassLoader classLoader){
        this.runtimeManager = runtimeManager;
        this.componentManager = componentManager;
        this.classLoader = classLoader;
    }
}
