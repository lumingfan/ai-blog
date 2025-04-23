package com.zeuslu.blog.common.exception;


import com.zeuslu.blog.common.errorcode.IErrorCode;
import lombok.Getter;

/**
 * @author lumingfan
 */
@Getter
public abstract class AbstractException extends RuntimeException {
    protected int code;
    protected String message;
    public AbstractException(IErrorCode errorCode) {
        this.code = errorCode.code();
        this.message = errorCode.message();
    }
    public AbstractException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
