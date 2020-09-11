package cn.np.boots.common.utils.reflect;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

@Slf4j
@Component
public class NpReflectUtils {
    public Class<?> classForName(String name, @Nullable ClassLoader classLoader) {
        try {
            return ClassUtils.forName(name, classLoader);
        } catch (Throwable e) {
            log.info("NP:classForName error", e);
            return null;
        }
    }

    public boolean isCglibProxyClass(Class<?> clazz){
        return ClassUtils.isCglibProxyClass(clazz);
    }
}
