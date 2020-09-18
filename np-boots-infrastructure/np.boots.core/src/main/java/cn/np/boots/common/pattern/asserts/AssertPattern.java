package cn.np.boots.common.pattern.asserts;

import org.apache.commons.lang3.StringUtils;

public class AssertPattern {

    public static <T> T notNull(T target) {
        if (target == null) {
            throw new NullPointerException();
        }
        return target;
    }

    public static <T> T notNull(T target, String msg,Object... args) {
        if (target == null) {
            throw new RuntimeException(String.format(msg,args));
        }
        return target;
    }

    public static <T> T getOrElse(T target,T defaultValue){
        if(target == null) return defaultValue;
        else return target;
    }

    public static String getStringOrElse(String target,String defaultValue){
        if(StringUtils.isEmpty(target)) return defaultValue;
        else return target;
    }
}
