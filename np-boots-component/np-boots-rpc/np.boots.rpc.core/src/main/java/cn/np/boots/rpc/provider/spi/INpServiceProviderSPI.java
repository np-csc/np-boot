package cn.np.boots.rpc.provider.spi;

import cn.np.boots.core.runtime.NpRuntimeManager;

import java.util.List;

public interface INpServiceProviderSPI {
    void register(NpRuntimeManager runtimeManager, NpServiceProviderInfo provider);
}
