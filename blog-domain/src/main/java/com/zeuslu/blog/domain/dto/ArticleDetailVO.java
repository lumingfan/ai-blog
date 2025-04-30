package com.zeuslu.blog.domain.dto;

import com.zeuslu.blog.domain.vo.ArticleItemVO;
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
