package com.zeuslu.blog.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * AI模型枚举
 * @author lumingfan
 */
@Getter
public enum AiModelEnums {
    OLLAMA("ollama"),
    GPT_3_5_TURBO("gpt-3.5-turbo"),
    GPT_4("gpt-4"),
    GPT_4_32K("gpt-4-32k"),
    ;

    @JsonValue
    private final String model;

    AiModelEnums(String model) {
        this.model = model;
    }

    @JsonCreator
    public static AiModelEnums fromValue(String value) {
        if (value == null) {
            return null;
        }

        for (AiModelEnums model : values()) {
            if (model.model.equalsIgnoreCase(value)) {
                return model;
            }
        }

        throw new IllegalArgumentException("无效的模型: " + value);
    }
}
