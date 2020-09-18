package cn.np.boots.common.pattern;

import cn.np.boots.common.pattern.asserts.get.NpGetPattern;
import cn.np.boots.common.pattern.log.NpLogPattern;
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
        return tryCatchGet(tryCode, null);
    }


    private static NpGetPattern get = new NpGetPattern();

    public static NpGetPattern get() {
        return get;
    }

    private static NpLogPattern log = new NpLogPattern();

    public static NpLogPattern log(){
        return log;
    }
}
