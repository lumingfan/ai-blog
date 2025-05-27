package com.zeuslu.blog.api.user.domain.dto;

import com.zeuslu.blog.api.user.constant.AuthDtoParamConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lumingfan
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "登录请求DTO")
public class LoginDTO {
    @Schema(description = "用户名")
    @Size(min = 3, max = 20, message = "用户名长度必须在3到20个字符之间")
    private String username;

    @Schema(description = "密码")
    @Pattern(regexp = AuthDtoParamConstant.PASSWORD_REGEX_PATTERN, message = "密码需要包含字母和数字，且长度在6到20个字符之间")
    private String password;

    @Schema(description = "手机号")
    @Pattern(regexp = AuthDtoParamConstant.PHONE_REGEX_PATTERN, message = "手机号格式不正确")
    private String phone;

    @Schema(description = "邮箱")
    @Email(message = "邮箱格式错误")
    private String email;

    @Schema(description = "验证码")
    private String code;

    @Schema(description = "登录类型(username/phone/email)")
    @NotBlank(message = "登录类型不能为空")
    private String type;
}