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
    public CommonException(Integer code, String message) {
        super(code, message);
    }

    @Override
    public String toString() {
        return "CommonException: {" +
                "code='" + getCode() + "', " +
                "message='" + getMessage() + "'" +
                '}';
    }

}
