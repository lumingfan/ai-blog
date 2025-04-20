package com.zeuslu.blog.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author lumingfan
 */
@Getter
public enum UserRoleEnums {
    VISITOR(0, "游客"),
    USER(1, "用户"),
    ADMIN(2, "管理员");
    @EnumValue
    private final int role;
    @JsonValue
    private final String desc;

    UserRoleEnums(int role, String desc) {
        this.role = role;
        this.desc = desc;
    }
}
