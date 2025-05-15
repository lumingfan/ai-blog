package com.zeuslu.blog.common.errorcode;

/**
 * @author lumingfan
 */
public enum AiErrorCode implements IErrorCode {
    UNSUPPORTED_MODEL_TYPE(90001, "不支持的模型"),
    ;

    private final int code;
    private final String message;

    AiErrorCode(int code, String message) {
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
