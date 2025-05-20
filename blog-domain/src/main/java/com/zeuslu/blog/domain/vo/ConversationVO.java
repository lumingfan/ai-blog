package com.zeuslu.blog.domain.vo;

import com.zeuslu.blog.common.enums.ChatMessageType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lumingfan
 */
@Schema(description = "聊天对话VO")
@Data
public class ConversationVO {
    @Schema(description = "对话ID")
    private Long id;
    @Schema(description = "最后一条消息")
    private String lastMessage;
    @Schema(description = "最后一条消息的类型")
    private ChatMessageType lastMessageType;
    @Schema(description = "最后一条消息的用户昵称")
    private String lastMessageNickname;
    @Schema(description = "未读消息数")
    private int unreadCount;
    @Schema(description = "对话参与者")
    private List<UserVO> participants;
    @Schema(description = "对话更新时间")
    private LocalDateTime updatedAt;
}
