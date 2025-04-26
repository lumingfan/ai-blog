package com.zeuslu.blog.common.errorcode;

/**
 * @author lumingfan
 */
public enum PostErrorCode implements IErrorCode {
    PUBLISH_FAILED(70000, "帖子发布失败, 请稍后重试");

    private final int code;
    private final String message;

    PostErrorCode(int code, String message) {
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
