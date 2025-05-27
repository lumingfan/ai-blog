package com.zeuslu.blog.common.errorcode;

/**
 * @author lumingfan
 */
public enum ArticleErrorCode implements IErrorCode {
    PUBLISH_FAILED(70000, "文章发布失败, 请稍后重试"),
    NOT_AUTHOR(70001, "你不是文章的作者"),
    NO_ARTICLE(70002, "文章不存在"),
    ;

    private final int code;
    private final String message;

    ArticleErrorCode(int code, String message) {
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
