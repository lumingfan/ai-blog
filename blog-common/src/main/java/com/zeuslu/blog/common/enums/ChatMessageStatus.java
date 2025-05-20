package com.zeuslu.blog.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 消息角色枚举
 * @author lumingfan
 */
@Getter
public enum ChatMessageStatus {
    SENDING("SENDING"),
    SENT("SENT"),
    DELIVERED("DELIVERED"),
    READ("READ"),
    FAILED("FAILED")
    ;

    @JsonValue
    private final String status;
    ChatMessageStatus(String status) {
        this.status = status;
    }

    @JsonCreator
    public static ChatMessageStatus fromValue(String value) {
        if (value == null) {
            return null;
        }

        for (ChatMessageStatus status : values()) {
            if (status.status.equalsIgnoreCase(value)) {
                return status;
            }
        }

        throw new IllegalArgumentException("无效的状态: " + value);
    }
}
