package cn.np.boots.common.utils.web;

import cn.np.boots.common.utils.NpUtils;
import cn.np.boots.common.utils.web.action.NpWebActionProcessor;
import cn.np.boots.common.utils.web.action.NpWebAsyncActionCallback;
import cn.np.boots.exception.NpRuntimeException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class NpWebActionUtils implements InitializingBean {
    private OkHttpClient client;
    @Override
    public void afterPropertiesSet() throws Exception {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        this.client = builder.build();
    }


    public class WebActionOptions {
        private Long connectTimeout;
        private Long readTimeout;
        private Long writeTimeout;
    }

    public WebActionOptions options() {
        return new WebActionOptions();
    }

    public NpWebActionProcessor build() {
        return new NpWebActionProcessor(this.client);
    }

    public NpWebActionProcessor post() {
        return new NpWebActionProcessor(this.client).method(NpWebActionProcessor.WebMethodType.POST);
    }

    public NpWebActionProcessor get() {
        return new NpWebActionProcessor(this.client).method(NpWebActionProcessor.WebMethodType.GET);
    }
}
