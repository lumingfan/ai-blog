package com.zeuslu.blog.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "邮箱验证码请求DTO")
public class EmailCodeDTO {
    @Schema(description = "邮箱")
    @Email(message = "邮箱格式错误")
    private String email;
}