package cn.np.boots.rpc.provider.spi;

import cn.np.boots.common.utils.NpUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NpServiceProviderInfo {
    private String serviceUniqueId;
    private String address;
    private String binding;
    private Class<?> contractClass;
    private Object contractInstance;

    private List<NpServiceBindingInfo> bindings = new ArrayList<>();

    public boolean containBinding(String binding) {
        if (NpUtils.type().string().isEmpty(binding)) return false;
        for (NpServiceBindingInfo bindingItem : this.bindings) {
            if (binding.equals(bindingItem.getBinding())) return true;
        }
        return false;
    }
}
