package com.zeuslu.blog.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录令牌视图对象
 * @author lumingfan
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "登录令牌视图对象")
public class TokenVO {
    /**
     * 访问令牌
     */
    @Schema(description = "访问令牌")
    private String token;

    /**
     * 用户基本信息
     */
    @Schema(description = "用户基本信息")
    private UserVO userInfo;
}