package com.zeuslu.blog.api.ai.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AI对话表
 * @author lumingfan
 */
@TableName(value ="tb_ai_session_conversation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiSessionConversation {
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 会话id
     */
    private Long  sessionId;

    /**
     * 对话id
     */
    private Long conversationId;
}