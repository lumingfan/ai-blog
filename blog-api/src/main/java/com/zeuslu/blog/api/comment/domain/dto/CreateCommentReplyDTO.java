package com.zeuslu.blog.api.comment.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 创建评论回复DTO
 * @author lumingfan
 */
@Schema(description = "创建评论回复DTO")
@Data
public class CreateCommentReplyDTO {
    @Schema(description = "评论id")
    @NotNull
    private Long commentId;
    @Schema(description = "评论内容")
    @NotBlank
    private String content;
    @Schema(description = "被回复的用户id")
    private Long atUserId;
}
