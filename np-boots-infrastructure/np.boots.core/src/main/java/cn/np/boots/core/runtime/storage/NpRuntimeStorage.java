package cn.np.boots.core.runtime.storage;

public interface NpRuntimeStorage {
    <T> T get(String key,Class<T> clazz);
}
