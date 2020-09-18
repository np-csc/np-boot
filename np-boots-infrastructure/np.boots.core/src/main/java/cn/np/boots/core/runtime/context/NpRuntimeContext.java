package cn.np.boots.core.runtime.context;

import cn.np.boots.core.runtime.NpRuntimeManager;
import cn.np.boots.core.runtime.component.NpComponentManager;
import cn.np.boots.core.runtime.storage.NpRuntimeStorage;
import lombok.Getter;

@Getter
public class NpRuntimeContext {
    private final NpRuntimeManager runtimeManager;
    private final NpComponentManager componentManager;
    private final ClassLoader classLoader;
    private final NpRuntimeStorage storage;

    public NpRuntimeContext(NpRuntimeManager runtimeManager, NpComponentManager componentManager, ClassLoader classLoader){
        this.runtimeManager = runtimeManager;
        this.componentManager = componentManager;
        this.classLoader = classLoader;
    }
}
