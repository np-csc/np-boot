package cn.np.boots.test.core.utils;

import cn.np.boots.common.utils.NpUtils;
import cn.np.boots.test.common.BaseTest;
import org.junit.Test;

import java.util.Date;

public class SerializeUtilsTest extends BaseTest {

    @lombok.Data
    class Data{
        private String string = "string";
        private Date date = new Date();
    }

    @Test
    public void jsonSerialize(){
        System.out.println(NpUtils.serialize().json().serialize(this));
    }
}
