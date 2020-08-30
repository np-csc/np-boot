package cn.np.boots.core.utils.web;

import java.io.IOException;
import java.util.Objects;

public class WebUtils {
    private void post(final String json) {
        try {
            String result = OkHttpTools.getInstance().post(this.url, json);
            if (Objects.equals(result, "success")) {
                log.info("http client register success :{} " + json);
            } else {
                log.error("http client register error :{} " + json);
            }
        } catch (IOException var3) {
            log.error("cannot register soul admin param :{}", this.url + ":" + json);
        }

    }

    public class PostActionExecutor{
        private String url;
        private String paramers;

        public void action(){

        }
    }
    public void post(){

    }
}
