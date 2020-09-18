package cn.np.boots.rpc.core.service;

import java.util.concurrent.ConcurrentHashMap;

public class NpServiceRegisterCenter {
    private ConcurrentHashMap<String,NpServiceRegisterInfo> center = new ConcurrentHashMap<>();

    public NpServiceRegisterInfo get(String serviceId){
        return center.get(serviceId);
    }
}
