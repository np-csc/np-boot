package cn.np.boots.common.utils.web.action;

import lombok.Data;
import okhttp3.Response;

import java.io.IOException;

@Data
public class NpWebActionResponse {
    private int code;
    private boolean isSuccessful;
    private String body;

    @Override
    public String toString() {
        return "NpWebActionResponse{" +
                "code=" + code +
                ", isSuccessful=" + isSuccessful +
                ", body='" + body + '\'' +
                '}';
    }

    static NpWebActionResponse from(Response response) throws IOException {
        NpWebActionResponse npWebActionResponse = new NpWebActionResponse();
        npWebActionResponse.setBody(response.body().string());
        npWebActionResponse.setSuccessful(response.isSuccessful());
        npWebActionResponse.setCode(response.code());
        return npWebActionResponse;
    }


}
