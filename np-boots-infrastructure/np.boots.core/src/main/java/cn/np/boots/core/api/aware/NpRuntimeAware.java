package cn.np.boots.core.api.aware;

import cn.np.boots.core.runtime.NpRuntimeManager;

public interface NpRuntimeAware {
    void setNpRuntimeManager(NpRuntimeManager runtimeManager);
}
