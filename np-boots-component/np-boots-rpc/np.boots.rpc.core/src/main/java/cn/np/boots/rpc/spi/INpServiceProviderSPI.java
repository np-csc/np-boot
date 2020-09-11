package cn.np.boots.rpc.spi;

import java.util.List;

public interface INpServiceProviderSPI {
    void register(List<NpServiceProviderInfo> providers);
}
