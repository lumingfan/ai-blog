package com.zeuslu.blog.api.user.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author lumingfan
 */
@Data
@Schema(description = "更新用户信息DTO")
public class UpdateUserDTO {
    @Schema(description = "头像")
    private String avatar;
    @Schema(description = "个人简介")
    private String bio;
    // TODO: 支持手机号, 邮箱, 密码, 标签
}

