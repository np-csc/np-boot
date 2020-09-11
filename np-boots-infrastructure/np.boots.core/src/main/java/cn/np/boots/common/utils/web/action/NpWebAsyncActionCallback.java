package cn.np.boots.common.utils.web.action;

import java.io.IOException;

public interface NpWebAsyncActionCallback {
    void onFailure(IOException e);
    void onResponse(NpWebActionResponse response);
}
