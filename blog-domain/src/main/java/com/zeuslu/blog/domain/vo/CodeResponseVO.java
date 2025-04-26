package com.zeuslu.blog.domain.vo;

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
@Schema(description = "验证码响应VO")
public class CodeResponseVO {
    @Schema(description = "是否发送成功")
    private Boolean success;
    
    @Schema(description = "结果消息")
    private String message;
    
    @Schema(description = "过期时间（秒）")
    private Integer expireTime;
}