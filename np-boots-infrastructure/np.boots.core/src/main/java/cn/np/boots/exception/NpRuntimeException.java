package cn.np.boots.exception;

public class NpRuntimeException extends RuntimeException {
    public NpRuntimeException(String msg) {
        super(msg);
    }

    public NpRuntimeException(Exception e) {
        super(e);
    }

    public NpRuntimeException(Exception e, String msg) {
        super(msg, e);
    }
}
