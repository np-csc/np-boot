package cn.np.boots.exception;

public class NpInvalidateValueRuntimeException extends NpRuntimeException {
    public NpInvalidateValueRuntimeException(String msg){
        super(msg);
    }
    public NpInvalidateValueRuntimeException(Exception e){
        super(e);
    }
}
