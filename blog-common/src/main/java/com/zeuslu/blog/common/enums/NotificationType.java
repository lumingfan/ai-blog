package com.zeuslu.blog.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author lumingfan
 */

@Getter
public enum NotificationType {
    LIKE("like"),
    COMMENT("comment"),
    FOLLOW("follow"),
    SYSTEM("system"),
    ;

    @JsonValue
    private final String type;

    NotificationType(String type) {
        this.type = type;
    }
    @JsonCreator
    public static NotificationType fromValue(String value) {
        if (value == null) {
            return null;
        }

        for (NotificationType type : values()) {
            if (type.type.equalsIgnoreCase(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException("无效的类型: " + value);
    }
}
