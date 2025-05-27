package com.zeuslu.blog.api.article.domain.vo;

import com.zeuslu.blog.api.user.domain.vo.UserVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lumingfan
 */
@Data
@SuperBuilder
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
    @Schema(description = "文章分类")
    private ArticleCategoryVO category;
    @Schema(description = "文章发布/更新时间")
    private LocalDateTime updatedAt;
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
