package com.zeuslu.blog.api.user.domain.dto;

import com.zeuslu.blog.api.user.constant.AuthDtoParamConstant;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "注册请求DTO")
public class RegisterDTO {
    @Schema(description = "用户名")
    @Size(min = 3, max = 20, message = "用户名长度必须在3到20个字符之间")
    private String username;
    
    @Schema(description = "密码")
    @Pattern(regexp = AuthDtoParamConstant.PASSWORD_REGEX_PATTERN, message = "密码需要包含字母和数字，且长度在6到20个字符之间")
    private String password;
    
    @Schema(description = "确认密码")
    @Pattern(regexp = AuthDtoParamConstant.PASSWORD_REGEX_PATTERN, message = "密码需要包含字母和数字，且长度在6到20个字符之间")
    private String repeatedPassword;
}