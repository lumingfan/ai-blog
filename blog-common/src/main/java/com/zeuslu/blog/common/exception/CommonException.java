package com.zeuslu.blog.common.exception;

import com.zeuslu.blog.common.errorcode.IErrorCode;
import lombok.Getter;

/**
 * @author lumingfan
 */
@Getter
public class CommonException extends AbstractException {
    public CommonException(IErrorCode code) {
        super(code);
    }

    @Override
    public String toString() {
        return "CommonException: {" +
                "code='" + errorCode.code() + "', " +
                "message='" + errorCode.message() + "'" +
                '}';
    }

}
