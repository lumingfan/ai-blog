package com.zeuslu.blog.common.domain;


import com.zeuslu.blog.common.constant.HttpStatus;
import com.zeuslu.blog.common.errorcode.IErrorCode;
import com.zeuslu.blog.common.exception.AbstractException;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 *
 * @author lumingfan
 */
@Data
public class Result<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public static final int SUCCESS = HttpStatus.SUCCESS;
    public static final int FAIL = HttpStatus.ERROR;
    public static final String SUCCESS_MSG = "操作成功!";
    public static final String FAIL_MSG = "操作失败!";
    
    private int code;
    private String msg;
    private T data;

    public static <T> Result<T> ok() {
        return build(null, SUCCESS, SUCCESS_MSG);
    }

    public static <T> Result<T> ok(T data) {
        return build(data, SUCCESS, SUCCESS_MSG);
    }

    public static <T> Result<T> ok(T data, String msg) {
        return build(data, SUCCESS, msg);
    }

    public static <T> Result<T> fail() {
        return build(null, FAIL, FAIL_MSG);
    }

    public static <T> Result<T> fail(String msg) {
        return build(null, FAIL, msg);
    }

    public static <T> Result<T> fail(T data) {
        return build(data, FAIL, FAIL_MSG);
    }

    public static <T> Result<T> fail(AbstractException ex) {
        return build(null, ex.code(), ex.message());
    }

    public static <T> Result<T> fail(T data, String msg) {
        return build(data, FAIL, msg);
    }

    public static <T> Result<T> fail(int code, String msg) {
        return build(null, code, msg);
    }

    private static <T> Result<T> build(T data, int code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }
}
