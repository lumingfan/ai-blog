package com.zeuslu.blog.common.errorcode;

/**
 * @author lumingfan
 */
public enum UserErrorCode implements IErrorCode {
    REPEATED_PASSWORD_NON_CONSISTENT(50000, "重复密码不一致"),
    USERNAME_OR_PASSWORD_ERROR(50001, "用户名或密码错误"),
    USERNAME_EXISTED(50002, "用户名已存在"),
    UNSUPPORTED_LOGIN_TYPE(50003, "不支持的登录方式"),
    USER_NOT_EXISTED(50004, "用户不存在"),
    TOO_MANY_REQUESTS(50029, "请求过于频繁"),
    INTERNAL_SERVER_ERROR(50030, "服务繁忙, 请稍后重试"),
    ;

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
