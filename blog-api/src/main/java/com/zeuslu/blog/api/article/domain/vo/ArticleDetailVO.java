package com.zeuslu.blog.api.article.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author lumingfan
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "文章详情VO")
public class ArticleDetailVO extends ArticleItemVO {
    @Schema(description = "文章内容")
    private String content;
}
