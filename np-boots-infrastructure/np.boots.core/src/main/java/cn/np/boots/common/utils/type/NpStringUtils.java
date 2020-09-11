package cn.np.boots.common.utils.type;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class NpStringUtils {
    public Boolean isNotEmpty(String value){
        return !isEmpty(value);
    }
    public Boolean isEmpty(String value){
        return StringUtils.isEmpty(value);
    }
}
