package com.zeuslu.blog.api.comment.domain.vo;

import com.zeuslu.blog.api.user.domain.vo.UserVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lumingfan
 */
@Schema(description = "获取评论回复VO")
@Data
public class CommentReplyVO {
    @Schema(description = "评论id")
    private Long id;
    @Schema(description = "评论内容")
    private String content;
    @Schema(description = "回复的评论id")
    private Long commentId;
    @Schema(description = "评论用户")
    private UserVO user;
    @Schema(description = "评论时间")
    private LocalDateTime createdAt;
    @Schema(description = "点赞数")
    private Integer likeCount;
    @Schema(description = "当前用户是否点赞")
    private Boolean isLiked;
    @Schema(description = "被@的用户(回复他人回复时使用)")
    private UserVO atUser;
}
