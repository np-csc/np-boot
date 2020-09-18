package cn.np.boots.rpc.jvm.spi;

import cn.np.boots.core.runtime.NpRuntimeManager;
import cn.np.boots.rpc.core.service.NpServiceRegisterInfo;
import cn.np.boots.rpc.provider.spi.INpServiceProviderSPI;
import cn.np.boots.rpc.provider.spi.NpServiceProviderInfo;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class NpRpcJvmProviderRegisterSPI implements INpServiceProviderSPI {
    @Override
    public void register(NpRuntimeManager runtimeManager, List<NpServiceProviderInfo> providers) {
        final ConfigurableApplicationContext applicationContext =  runtimeManager.getApplicationContext();

        providers.forEach(provider->{
            NpServiceRegisterInfo registerInfo = NpServiceRegisterInfo.builder().build();

            provider.getContractInstance()
        });
        //applicationContext.getBeanFactory().
    }
}
