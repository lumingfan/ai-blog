package com.zeuslu.blog.api.comment.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zeuslu.blog.common.enums.CommentType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评论表
 * @author lumingfan
 */
@Data
@TableName("tb_comment")
public class Comment {
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论的对象(视频, 文章)
     */
    private Long subjectId;

    /**
     * 被评论对象类型(视频, 文章)
     */
    private CommentType subjectType;

    /**
     * 发起评论的用户id
     */
    private Long userId;
    private LocalDateTime createdAt;
}
