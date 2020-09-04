package cn.np.boots.test.core.utils;

import cn.np.boots.common.utils.NpUtils;
import cn.np.boots.common.utils.web.action.NpWebActionResponse;
import cn.np.boots.common.utils.web.action.NpWebAsyncActionCallback;
import cn.np.boots.test.common.BaseTest;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WebUtilsTest extends BaseTest {
    @Test
    public void post(){
        String url = "http://10.222.83.218:8080/jrcs/api/ivr/user/level";
        Map<String,String> data = new HashMap<>();
        data.put("jdpin","huangdashuai");
        data.put("clientId","test");
        data.put("token","97283477ea60");


        NpWebActionResponse result = NpUtils.web().action().post().url(url).data(data).syncOrElse(null);
        System.out.println(result.getBody());
    }

    @Test
    public void postAsync() throws Exception{
        String url = "http://10.222.83.218:8080/jrcs/api/ivr/user/level";
        Map<String,String> data = new HashMap<>();
        data.put("jdpin","huangdashuai");
        data.put("clientId","test");
        data.put("token","97283477ea60");


        NpUtils.web().action().post().url(url).data(data).async(new NpWebAsyncActionCallback() {
            @Override
            public void onFailure(IOException e) {
                throw new RuntimeException(e);
            }

            @Override
            public void onResponse(NpWebActionResponse response) {
                System.out.println(response.getBody());
            }
        });

        System.out.println("process end");
        Thread.sleep(3000);
    }
}
