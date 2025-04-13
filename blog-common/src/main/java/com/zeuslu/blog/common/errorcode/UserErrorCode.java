package com.zeuslu.blog.common.errorcode;

/**
 * @author lumingfan
 */
public enum UserErrorCode implements IErrorCode {
    BAD_REPEATED_PASSWORD(60000, "密码不一致"),
    USER_EXISTED(60001, "用户已存在");

    private final int code;
    private final String message;

    UserErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.message;
    }
}
