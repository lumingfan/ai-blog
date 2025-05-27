package com.zeuslu.blog.api.chat.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 聊天对话用户关联表
 * @author lumingfan
 */
@TableName(value ="tb_chat_conversation_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatConversationUser {
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
     * 关联的用户id
     */
    private Long userId;

    /**
     * 用户最后一次离开群聊的时间
     */
    private LocalDateTime lastLeaveTime;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}