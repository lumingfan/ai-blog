package com.zeuslu.blog.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户注册数据传输对象
 * @author lumingfan
 */
@Schema(description = "用户注册数据传输对象")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {
    /**
     * 用户账号名
     */
    @Schema(description = "用户账号名")
    private String username;

    /**
     * 用户密码
     */
    @Schema(description = "用户密码")
    private String password;

    /**
     * 用户重复输入密码
     */
    @Schema(description = "用户重复输入密码")
    private String repeatedPassword;
}