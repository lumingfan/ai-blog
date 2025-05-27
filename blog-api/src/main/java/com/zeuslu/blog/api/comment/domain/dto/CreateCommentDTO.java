package com.zeuslu.blog.api.comment.domain.dto;

import com.zeuslu.blog.common.enums.CommentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 创建评论DTO
 * @author lumingfan
 */
@Schema(description = "创建评论DTO")
@Data
public class CreateCommentDTO {
    @Schema(description = "评论对象id(文章id,视频id)")
    @NotNull
    private Long subjectId;
    @Schema(description = "评论对象类型")
    private CommentType subjectType;
    @Schema(description = "评论内容")
    @NotBlank
    private String content;
    @Schema(description = "评论的父评论id")
    private Long parentId;
}
