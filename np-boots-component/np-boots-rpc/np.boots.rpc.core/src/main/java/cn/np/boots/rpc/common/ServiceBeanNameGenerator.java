package cn.np.boots.rpc.common;

import cn.np.boots.common.utils.NpUtils;
import org.springframework.stereotype.Component;

@Component
public class ServiceBeanNameGenerator {

    private static final String SERVICE_BEAN_NAME_PREFIX = "NpServiceBean#";

    public String generateServiceBeanName(Class<?> clazz, String uid) {
        if (NpUtils.type().string().isEmpty(uid)) {
            return SERVICE_BEAN_NAME_PREFIX + clazz.getCanonicalName();
        }
        return SERVICE_BEAN_NAME_PREFIX + clazz.getCanonicalName() + ":" + uid;
    }
}
