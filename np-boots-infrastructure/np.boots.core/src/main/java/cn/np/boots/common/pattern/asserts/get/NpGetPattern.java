package cn.np.boots.common.pattern.asserts.get;

import cn.np.boots.common.utils.NpUtils;

public class NpGetPattern {
    public String getStringOrEmptyElse(String value,String defaultValue){
        if(NpUtils.type().string().isEmpty(value)){
            return defaultValue;
        }
        return value;
    }
}
