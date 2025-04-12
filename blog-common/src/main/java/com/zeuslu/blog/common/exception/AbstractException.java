package com.zeuslu.blog.common.exception;


import com.zeuslu.blog.common.errorcode.IErrorCode;
import lombok.Getter;

/**
 * @author lumingfan
 */
public abstract class AbstractException extends RuntimeException {
    protected IErrorCode errorCode;
    public AbstractException(IErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public int code() {
        return errorCode.code();
    }
    public String message() {
        return errorCode.message();
    }
}
