package com.zeuslu.blog.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author lumingfan
 */

@Getter
public enum CommentType {
    ARTICLE("article"),
    ;

    @JsonValue
    private final String type;

    CommentType(String type) {
        this.type = type;
    }
    @JsonCreator
    public static CommentType fromValue(String value) {
        if (value == null) {
            return null;
        }

        for (CommentType type : values()) {
            if (type.type.equalsIgnoreCase(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException("无效的类型: " + value);
    }
}
