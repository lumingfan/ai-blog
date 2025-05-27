package com.zeuslu.blog.api.ai.domain.vo;

import com.zeuslu.blog.common.enums.AiMessageRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lumingfan
 */
@Schema(description = "AI消息VO")
@Data
public class AiMessageVO {
    @Schema(description = "会话ID")
    private Long sessionId;
    @Schema(description = "消息内容")
    private String content;
    @Schema(description = "消息角色")
    private AiMessageRole role;
    @Schema(description = "时间戳")
    private LocalDateTime timestamp;
}
