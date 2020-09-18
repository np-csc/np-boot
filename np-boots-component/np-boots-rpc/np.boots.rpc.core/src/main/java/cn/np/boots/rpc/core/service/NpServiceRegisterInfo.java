package cn.np.boots.rpc.core.service;

import cn.np.boots.core.runtime.NpRuntimeManager;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.ConfigurableApplicationContext;

@Data
@Builder
public class NpServiceRegisterInfo<T> {
    private NpRuntimeManager runtimeManager;
    private ConfigurableApplicationContext applicationContext;
    private ClassLoader classLoader;
    private String serviceId;
    private String serviceName;
    private Class<T> serviceContract;
    private T serviceImpl;
}
