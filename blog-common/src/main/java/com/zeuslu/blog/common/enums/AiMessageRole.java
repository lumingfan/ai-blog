package com.zeuslu.blog.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 消息角色枚举
 * @author lumingfan
 */
@Getter
public enum AiMessageRole {
    SYSTEM("system"),
    USER("user"),
    ASSISTANT("assistant"),
    ;

    @JsonValue
    private final String role;
    AiMessageRole(String role) {
        this.role = role;
    }

    @JsonCreator
    public static AiMessageRole fromValue(String value) {
        if (value == null) {
            return null;
        }

        for (AiMessageRole role : values()) {
            if (role.role.equalsIgnoreCase(value)) {
                return role;
            }
        }

        throw new IllegalArgumentException("无效的角色: " + value);
    }
}
