package cn.np.boots.core.pattern;

import org.springframework.lang.Nullable;

import java.util.function.Supplier;

public class NpPattern {
    public static <T> T tryCatchGet(Supplier<T> tryCode, T exceptionValue) {
        try {
            return tryCode.get();
        } catch (Throwable e) {
            // ignore
            return exceptionValue;
        }
    }

    @Nullable
    public static <T> T tryCatchGet(Supplier<T> tryCode) {
        return tryCatchGet(tryCode,null);
    }
}
