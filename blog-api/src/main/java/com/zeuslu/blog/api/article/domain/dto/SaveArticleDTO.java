package com.zeuslu.blog.api.article.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * @author lumingfan
 */
@Data
@Schema(description = "保存文章DTO")
public class SaveArticleDTO {
    @Schema(description = "文章ID")
    @NotNull
    private Long id;
    @Schema(description = "文章标题")
    private String title;
    @Schema(description = "文章内容")
    private String content;
    @Schema(description = "文章摘要")
    private String summary;
    @Schema(description = "文章封面")
    private String coverImage;
    @Schema(description = "文章分类")
    private Long categoryId;
    @Schema(description = "文章标签")
    private List<String> tags;
    @Schema(description = "文章是否为草稿")
    private Boolean draft;
}
