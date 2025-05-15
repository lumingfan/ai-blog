package com.zeuslu.blog.domain.dto;

import com.zeuslu.blog.common.enums.AiModelEnums;
import com.zeuslu.blog.domain.biz.AiMessageContent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author lumingfan
 */
@Schema(description = "AI消息DTO")
@Data
public class AiMessageDTO {
    @Schema(description = "会话ID")
    private Long sessionId;
    @Schema(description = "消息内容")
    private AiMessageContent content;
    @Schema(description = "模型")
    private AiModelEnums model;
}
