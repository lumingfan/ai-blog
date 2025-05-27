package com.zeuslu.blog.api.chat.domain.po;

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
 * 聊天对话表
 * @author lumingfan
 */
@TableName(value ="tb_chat_conversation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatConversation {
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 最后一条消息id
     */
    private Long lastMessageId;

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