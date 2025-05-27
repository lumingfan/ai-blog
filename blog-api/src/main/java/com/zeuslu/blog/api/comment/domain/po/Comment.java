package com.zeuslu.blog.api.comment.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zeuslu.blog.common.enums.CommentType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 点赞模块
 * @author lumingfan
 */
@Data
@TableName("tb_comment")
public class Comment {
    @TableId
    private Long id;
    private String content;
    private Long subjectId;
    private CommentType subjectType;
    private Long userId;
    private LocalDateTime createdAt;
}
