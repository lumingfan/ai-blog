package com.zeuslu.blog.api.comment.domain.vo;

import com.zeuslu.blog.api.user.domain.vo.UserVO;
import com.zeuslu.blog.common.enums.CommentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 创建评论DTO
 * @author lumingfan
 */
@Schema(description = "获取评论响应VO")
@Data
public class CommentVO {
    @Schema(description = "评论id")
    private Long id;
    @Schema(description = "评论内容")
    private String content;
    @Schema(description = "评论用户")
    private UserVO user;
    @Schema(description = "评论时间")
    private LocalDateTime createdAt;
    @Schema(description = "点赞数")
    private Integer likeCount;
    @Schema(description = "当前用户是否点赞")
    private Boolean isLiked;
    @Schema(description = "回复评论数")
    private Integer replyCount;
    @Schema(description = "被@的用户(回复二级评论时使用)")
    private UserVO atUser;
    @Schema(description = "被评论对象id(文章id, 视频id)")
    private Long subjectId;
    @Schema(description = "被评论对象类型")
    private CommentType subjectType;
}
