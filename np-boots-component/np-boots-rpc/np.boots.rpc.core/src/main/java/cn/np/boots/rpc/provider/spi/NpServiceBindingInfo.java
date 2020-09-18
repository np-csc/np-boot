package cn.np.boots.rpc.provider.spi;

import lombok.Data;

@Data
public class NpServiceBindingInfo {
    private String address;
    private String binding;
    private Class<?> contract;
}
