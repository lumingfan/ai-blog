package com.zeuslu.blog.api.comment.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评论回复表
 * @author lumingfan
 */
@Data
@TableName("tb_comment_reply")
public class CommentReply {
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 回复内容
     */
    private String content;

    /**
     * 回复的评论id
     */
    private Long commentId;

    /**
     * 发起回复的用户id
     */
    private Long userId;

    /**
     * 被回复的用户id(仅当用户回复他人回复时使用, 回复评论使用commentId)
     */
    private Long atUserId;

    private LocalDateTime createdAt;
}
