package com.zeuslu.blog.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 评论回复事件
 * @author lumingfan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentReplyEvent {
    /**
     * 回复的评论ID
     */
    private Long commentId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论者ID
     */
    private Long commenterId;

    /**
     * 被回复的用户id(仅当回复他人回复时使用, 回复评论使用commentId)
     */
    private Long atUserId;
}
