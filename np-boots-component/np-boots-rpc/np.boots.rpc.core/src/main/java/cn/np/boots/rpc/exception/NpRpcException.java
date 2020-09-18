package cn.np.boots.rpc.exception;

import cn.np.boots.exception.NpRuntimeException;

public class NpRpcException extends NpRuntimeException {
    public NpRpcException(String msg) {
        super(msg);
    }

    public NpRpcException(Exception e) {
        super(e);
    }

    public NpRpcException(Exception e, String msg) {
        super(e, msg);
    }
}
