package com.zeuslu.blog.common.enums.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author lumingfan
 */
@Getter
public enum PostStatusEnums {
    DRAFT(0, "草稿"),
    PUBLISHED(1, "已发布"),
    DELETED(2, "已删除");

    @EnumValue
    @JsonValue
    private final Integer code;
    private final String desc;

    PostStatusEnums(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
