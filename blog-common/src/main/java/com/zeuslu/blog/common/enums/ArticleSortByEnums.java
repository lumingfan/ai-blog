package com.zeuslu.blog.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author lumingfan
 */
@Getter
public enum ArticleSortByEnums {
    NEWEST("newest"),
    POPULAR("popular"),
    RECOMMENDED("recommended")
    ;

    @JsonValue
    private final String sortBy;
    ArticleSortByEnums(String sortBy) {
        this.sortBy = sortBy;
    }

    @JsonCreator
    public static ArticleSortByEnums fromValue(String value) {
        if (value == null) {
            return null;
        }

        for (ArticleSortByEnums sortBy : values()) {
            if (sortBy.sortBy.equalsIgnoreCase(value)) {
                return sortBy;
            }
        }

        throw new IllegalArgumentException("无效的排序类型: " + value);
    }
}
