package com.zeuslu.blog.api.chat.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zeuslu.blog.common.enums.ChatMessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 聊天消息表
 * @author lumingfan
 */
@TableName(value ="tb_chat_message")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 关联的对话id
     */
    private Long conversationId;

    /**
     * 消息发送者id
     */
    private Long senderId;

    /**
     * 消息类型
     */
    private ChatMessageType type;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}