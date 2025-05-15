package com.zeuslu.blog.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 消息内容枚举
 * @author lumingfan
 */
@Getter
public enum AiMessageContentType {
    TEXT("text"),
    IMAGE("image"),
    ;

    @JsonValue
    private final String type;

    AiMessageContentType(String type) {
        this.type = type;
    }
    @JsonCreator
    public static AiMessageContentType fromValue(String value) {
        if (value == null) {
            return null;
        }

        for (AiMessageContentType type : values()) {
            if (type.type.equalsIgnoreCase(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException("无效的类型: " + value);
    }
}
