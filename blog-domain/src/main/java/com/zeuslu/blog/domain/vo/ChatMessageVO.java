package com.zeuslu.blog.domain.vo;

import com.zeuslu.blog.common.enums.ChatMessageType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author lumingfan
 */
@Schema(description = "聊天消息VO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageVO {
    @Schema(description = "消息ID")
    private Long id;
    @Schema(description = "对话ID")
    private Long conversationId;
    @Schema(description = "发送者信息")
    private UserVO sender;
    @Schema(description = "消息类型")
    private ChatMessageType type;
    @Schema(description = "消息内容")
    private String content;
    @Schema(description = "消息发送时间")
    private LocalDateTime createdAt;
}
