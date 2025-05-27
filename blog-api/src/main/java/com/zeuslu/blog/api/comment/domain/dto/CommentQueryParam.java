package com.zeuslu.blog.api.comment.domain.dto;

import com.zeuslu.blog.common.domain.PageQuery;
import com.zeuslu.blog.common.enums.CommentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lumingfan
 */
@EqualsAndHashCode(callSuper = true)
@Schema(description = "评论分页查询参数")
@Data
public class CommentQueryParam extends PageQuery {
    @Schema(description = "评论对象ID")
    private Long subjectId;
    @Schema(description = "评论对象类型")
    private CommentType subjectType;
}
