package cn.np.boots.common.utils.web.action;

import cn.np.boots.common.utils.NpUtils;
import cn.np.boots.exception.NpRuntimeException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class NpWebActionProcessor {
    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    public enum WebMediaType {JSON, FORM}

    public enum WebMethodType {GET, POST, PUT, DELETE}

    protected Map<String, String> headers;
    protected String url;
    protected Object data;
    protected WebMediaType mediaType = WebMediaType.JSON;
    protected WebMethodType method = WebMethodType.POST;

    private OkHttpClient executeClient;

    public NpWebActionProcessor(OkHttpClient executeClient) {
        this.executeClient = executeClient;
    }

    public NpWebActionProcessor head(String headerName, String value) {
        if (this.headers == null) {
            this.headers = new HashMap<String, String>();
        }
        this.headers.put(headerName, value);
        return this;
    }

    public NpWebActionProcessor url(String url) {
        this.url = url;
        return this;
    }

    public NpWebActionProcessor data(Object data) {
        this.data = data;
        return this;
    }

    public NpWebActionProcessor mediaType(WebMediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public NpWebActionProcessor method(WebMethodType method) {
        this.method = method;
        return this;
    }

    private Request buildRequest() {
        if (NpUtils.type().string().isEmpty(this.url)) {
            throw new NpRuntimeException("WebActionExecutor request url can not be empty");
        }

        okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder();

        if (this.headers != null) {
            this.headers.forEach((key, value) -> requestBuilder.header(key, value));
        }

        RequestBody body;
        switch (this.mediaType) {
            case JSON:
                body = RequestBody.create(JSON_MEDIA_TYPE, NpUtils.serialize().json().serialize(data));
                break;
            default:
                throw new NpRuntimeException("WebActionExecutor unsupported mediatype" + this.mediaType.name());
        }
        switch (this.method) {
            case GET:
                requestBuilder.url(url);
            case POST:
                requestBuilder.post(body).url(url);
                break;
            case PUT:
                requestBuilder.put(body).url(url);
                break;
            case DELETE:
                requestBuilder.delete(body).url(url);
                break;
            default:
                throw new NpRuntimeException("WebActionExecutor unsupported method" + this.method.name());
        }

        Request request = requestBuilder.build();
        return request;
    }

    public NpWebActionResponse sync() throws IOException {
        Request request = this.buildRequest();
        Response response = this.executeClient.newCall(request).execute();
        return NpWebActionResponse.from(response);
    }

    public NpWebActionResponse syncOrElse(NpWebActionResponse defaultValue) {
        try {
            return this.sync();
        } catch (Exception e) {
            String a = NpUtils.serialize().json().serialize(this);
            log.info("NP: WebAction process error,parameter:" + NpUtils.serialize().json().serialize(this), e);
            return defaultValue;
        }
    }

    public void async(NpWebAsyncActionCallback callback){
        Request request = this.buildRequest();
        Call call = this.executeClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                callback.onResponse(NpWebActionResponse.from(response));
            }
        });
    }
}
