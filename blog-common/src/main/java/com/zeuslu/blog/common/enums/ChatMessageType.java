package com.zeuslu.blog.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 消息角色枚举
 * @author lumingfan
 */
@Getter
public enum ChatMessageType {
    TEXT("TEXT"),
    IMAGE("IMAGE"),
    FILE("FILE"),
    SYSTEM("SYSTEM")
    ;

    @JsonValue
    private final String type;
    ChatMessageType(String type) {
        this.type = type;
    }

    @JsonCreator
    public static ChatMessageType fromValue(String value) {
        if (value == null) {
            return null;
        }

        for (ChatMessageType type : values()) {
            if (type.type.equalsIgnoreCase(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException("无效的类型: " + value);
    }
}
