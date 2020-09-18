package cn.np.boots.rpc.core.service;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
public abstract class NpServiceBean {
    public static  final String PROPERTY_SERVICE_BEAN_NAME = "serviceBeanName";
    public static  final String PROPERTY_SERVICE_UNIQUE_ID = "serviceUniqueId";
    public static  final String PROPERTY_SERVICE_CONTRACT_TYPE_NAME = "serviceContractTypeName";
    public static  final String PROPERTY_SERVICE_CONTRACT_TYPE = "serviceContractType";
    protected String serviceBeanName;
    protected String serviceUniqueId;
    protected String serviceContractTypeName;
    protected Class<?> serviceContractType;

    @Autowired
    protected SofaRuntimeContext      sofaRuntimeContext;
}
