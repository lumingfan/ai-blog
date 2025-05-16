package com.zeuslu.blog.common.errorcode;

/**
 * @author lumingfan
 */
public enum AiErrorCode implements IErrorCode {
    UNSUPPORTED_MODEL_TYPE(90001, "不支持的模型"),
    NOT_FOUND_SESSION(90002, "会话不存在"),
    STREAM_RESULT_SERIALIZE_ERROR(90003, "流式结果序列化错误"),
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
