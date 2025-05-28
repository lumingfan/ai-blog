package com.zeuslu.blog.api.comment.domain.dto;

import com.zeuslu.blog.common.domain.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lumingfan
 */
@EqualsAndHashCode(callSuper = true)
@Schema(description = "评论回复分页查询参数")
@Data
public class CommentReplyQueryParam extends PageQuery {
    @Schema(description = "评论ID")
    private Long commentId;
}
