package cn.np.boots.common.utils;

import cn.np.boots.common.utils.serialize.NpSerializeUtils;
import cn.np.boots.common.utils.spring.NpSpringUtils;
import cn.np.boots.common.utils.spring.context.NpSpringApplicationContextUtils;
import cn.np.boots.common.utils.tool.NpToolUtils;
import cn.np.boots.common.utils.type.NpTypeUtils;
import cn.np.boots.common.utils.value.NpValueUtils;
import cn.np.boots.common.utils.web.NpWebUtils;

public class NpUtils {
    public static NpTypeUtils type() {
        return NpSpringApplicationContextUtils.getStaticApplicationContext().getBean(NpTypeUtils.class);
    }

    public static NpWebUtils web() {
        return NpSpringApplicationContextUtils.getStaticApplicationContext().getBean(NpWebUtils.class);
    }

    public static NpSerializeUtils serialize() {
        return NpSpringApplicationContextUtils.getStaticApplicationContext().getBean(NpSerializeUtils.class);
    }

    public static NpSpringUtils spring() {
        return NpSpringApplicationContextUtils.getStaticApplicationContext().getBean(NpSpringUtils.class);
    }

    public static NpValueUtils value() {
        return NpSpringApplicationContextUtils.getStaticApplicationContext().getBean(NpValueUtils.class);
    }

    public static NpToolUtils tool() {
        return NpSpringApplicationContextUtils.getStaticApplicationContext().getBean(NpToolUtils.class);
    }
}
