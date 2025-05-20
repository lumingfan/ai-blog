package com.zeuslu.blog.domain.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
     * 用户未读消息数量
     */
    private Integer unreadCount;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField(fill= FieldFill.UPDATE)
    private LocalDateTime updatedAt;
}