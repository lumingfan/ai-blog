package com.zeuslu.blog.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lumingfan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "文章列表项VO")
public class ArticleItemVO {
    @Schema(description = "文章ID")
    private String id;
    @Schema(description = "文章标题")
    private String title;
    @Schema(description = "文章摘要")
    private String summary;
    @Schema(description = "文章封面")
    private String coverImage;
    @Schema(description = "文章作者")
    private UserVO author;
    @Schema(description = "文章发布时间")
    private LocalDateTime publishedAt;
    @Schema(description = "文章阅读数")
    private Integer readCount;
    @Schema(description = "文章点赞数")
    private Integer likeCount;
    @Schema(description = "文章评论数")
    private Integer commentCount;
    @Schema(description = "文章标签")
    private List<String> tags;
    @Schema(description = "当前用户是否点赞了该文章")
    private Boolean isLiked;
}
