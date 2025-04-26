package com.zeuslu.blog.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
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
@Schema(description = "忘记密码DTO")
public class ForgotPasswordDTO {
    @Schema(description = "邮箱")
    @Email(message = "邮箱格式错误")
    private String email;
}