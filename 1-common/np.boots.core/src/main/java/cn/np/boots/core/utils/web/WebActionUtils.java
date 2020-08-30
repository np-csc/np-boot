package cn.np.boots.core.utils.web;

import cn.np.boots.core.exception.NpException;
import cn.np.boots.core.utils.NpUtils;
import okhttp3.*;
import okhttp3.OkHttpClient.Builder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class WebActionUtils {
    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client;

    public enum WebMediaType {
        JSON, FORM
    }

    public enum WebMethodType {
        GET, POST, PUT, DELETE
    }

    public class WebActionExecutor {
        private OkHttpClient executeClient;

        public WebActionExecutor(OkHttpClient executeClient) {
            this.executeClient = executeClient;
        }

        private Map<String,String> headers;
        private String url;
        private Object data;
        private WebMediaType mediaType = WebMediaType.JSON;
        private WebMethodType method = WebMethodType.POST;

        public WebActionExecutor head(String headerName,String value){
            if(this.headers == null){
                this.headers = new HashMap<String, String>();
            }
            this.headers.put(headerName,value);
            return this;
        }
        public WebActionExecutor url(String url){
            this.url = url;
            return this;
        }
        public WebActionExecutor data(Object data){
            this.data = data;
            return this;
        }
        public WebActionExecutor mediaType(WebMediaType mediaType){
            this.mediaType = mediaType;
            return this;
        }
        public WebActionExecutor method(WebMethodType method){
            this.method = method;
            return this;
        }


        private Request buildRequest() {
            if (NpUtils.Type.String.isEmpty(this.url)) {
                throw new NpException("WebActionExecutor request url can not be empty");
            }

            okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder();

            if(this.headers != null){
//                this.headers.forEach((key,item)->{
//
//                });
            }

            RequestBody body;
            switch (this.mediaType) {
                case JSON:
                    body = RequestBody.create(JSON_MEDIA_TYPE, NpUtils.Serialize.Json.serialize(data));
                    break;
                default:
                    throw new NpException("WebActionExecutor unsupported mediatype" + this.mediaType.name());
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
                    throw new NpException("WebActionExecutor unsupported method" + this.method.name());
            }


            Request request = requestBuilder.build();
            return request;
        }

        public String sync() throws IOException {
            Request request = this.buildRequest();
            Response response = this.executeClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
            throw new IOException("Unexpected code " + response);
        }
    }

    public class WebActionOptions {
        private Long connectTimeout;
        private Long readTimeout;
        private Long writeTimeout;
    }

    public class WebPostActionExecutor {
        private String url;
        private String data;

        public void execute() throws IOException {
            Builder builder = new Builder();
            builder.connectTimeout(10L, TimeUnit.SECONDS);
            builder.readTimeout(10L, TimeUnit.SECONDS);
            builder.writeTimeout(10L, TimeUnit.SECONDS);
            OkHttpClient client = builder.build();


            RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, this.data);
            Request request = (new okhttp3.Request.Builder()).url(url).post(body).build();
            client.newCall(request).execute().body().string();
        }
    }


    public void post() {

    }
}
