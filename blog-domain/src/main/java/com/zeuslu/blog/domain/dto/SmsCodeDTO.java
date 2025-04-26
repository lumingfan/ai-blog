package com.zeuslu.blog.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
@Schema(description = "短信验证码请求DTO")
public class SmsCodeDTO {
    @Schema(description = "手机号")
    @NotBlank(message = "手机号不能为空")
    private String phone;
}