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
@Schema(description = "用户名可用性检查响应VO")
public class UsernameCheckVO {
    @Schema(description = "是否可用")
    private Boolean available;
    
    @Schema(description = "提示消息")
    private String message;
}