package com.zeuslu.blog.common.event;

import com.zeuslu.blog.common.enums.CommentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 评论事件
 * @author lumingfan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentEvent {
    /**
     * 目标ID
     */
    private Long targetId;

    /**
     * 目标类型
     */
    private CommentType type;


    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论者ID
     */
    private Long commenterId;
}
