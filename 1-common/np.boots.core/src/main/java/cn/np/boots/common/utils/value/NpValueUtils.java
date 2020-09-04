package cn.np.boots.common.utils.value;

import org.springframework.stereotype.Component;

@Component
public class NpValueUtils {
    public <T> T getOrElse(T value, T defaultValue) {
        if (value == null) return value;
        return defaultValue;
    }
}
