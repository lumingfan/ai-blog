package com.zeuslu.blog.api.chat.domain.dto;

import com.zeuslu.blog.common.enums.ChatMessageType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author lumingfan
 */
@Data
@Schema(description = "发送消息DTO")
public class ChatMessageDTO {
    @Schema(description = "对话id")
    private Long conversationId;
    @Schema(description = "消息类型")
    private ChatMessageType type;
    @Schema(description = "消息内容")
    private String content;
}

