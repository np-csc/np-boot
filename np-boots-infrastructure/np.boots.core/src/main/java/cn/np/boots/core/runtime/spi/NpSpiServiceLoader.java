package cn.np.boots.core.runtime.spi;

import java.util.Set;

public interface NpSpiServiceLoader {
    <T> Set<T> load(Class<T> tClass);
}
