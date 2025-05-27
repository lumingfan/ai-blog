package com.zeuslu.blog.api.user.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "登录响应VO")
public class LoginResponseVO {
    @Schema(description = "JWT令牌")
    private String token;
    
    @Schema(description = "用户信息")
    private UserVO user;
}