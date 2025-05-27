package com.zeuslu.blog.api.user.domain.dto;

import com.zeuslu.blog.api.user.constant.AuthDtoParamConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
@Schema(description = "重置密码DTO")
public class ResetPasswordDTO {
    @Schema(description = "重置令牌")
    @NotBlank(message = "重置令牌不能为空")
    private String token;
    
    @Schema(description = "新密码")
    @Pattern(regexp = AuthDtoParamConstant.PASSWORD_REGEX_PATTERN, message = "新密码需要包含字母和数字，且长度在6到20个字符之间")
    private String password;
}