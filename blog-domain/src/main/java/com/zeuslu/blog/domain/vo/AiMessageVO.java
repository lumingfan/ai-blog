package com.zeuslu.blog.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author lumingfan
 */
@Schema(description = "AI消息VO")
public class AiMessageVO {
    @Schema(description = "会话ID")
    private Long sessionId;
    @Schema(description = "消息内容")
    private String content;
}
