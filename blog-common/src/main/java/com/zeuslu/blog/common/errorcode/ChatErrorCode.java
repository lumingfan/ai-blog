package com.zeuslu.blog.common.errorcode;

/**
 * @author lumingfan
 */
public enum ChatErrorCode implements IErrorCode {
    CREATE_CHAT_FAILED(100001, "创建聊天失败, 请稍后重试"),
    DELETE_CHAT_FAILED(100002, "删除聊天失败, 请稍后重试"),
    SEND_MESSAGE_FAILED(100003, "发送消息失败, 请稍后重试"),
    DELETE_MESSAGE_FAILED(100004, "撤回消息失败, 请稍后重试")
    ;

    private final int code;
    private final String message;

    ChatErrorCode(int code, String message) {
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
