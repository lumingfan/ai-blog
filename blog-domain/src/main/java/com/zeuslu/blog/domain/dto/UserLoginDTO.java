package com.zeuslu.blog.domain.dto;

import com.zeuslu.blog.domain.constant.DtoParamConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 用户登录数据传输对象
 *
 * @author lumingfan
 */
@Schema(description = "用户登录数据传输对象")
public class UserLoginDTO {
    /**
     * 用户账号名
     */
    @Schema(description = "用户账号名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 用户密码
     */
    @Schema(description = "用户密码")
    @Size(min= DtoParamConstant.PASSWORD_MIN_LENGTH, max=DtoParamConstant.PASSWORD_MAX_LENGTH)
    private String password;
}
