package com.zeuslu.blog.api.article.domain.dto;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeuslu.blog.common.constant.TableFieldConstant;
import com.zeuslu.blog.common.domain.PageQuery;
import com.zeuslu.blog.api.article.constant.ArticleFieldConstant;
import com.zeuslu.blog.common.enums.ArticleSortByEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author lumingfan
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "文章查询参数")
public class ArticlePageQuery extends PageQuery {
    @Schema(description = "文章分类ID")
    private Long categoryId;

    @Schema(description = "文章标签")
    private List<String> tags;

    @Schema(description = "作者id")
    private Long authorId ;

    @Schema(description = "排序方式")
    private ArticleSortByEnums sortBy;


    @Override
    public <T> Page<T> toPage() {
        Page<T> page = super.toPage();
        switch (sortBy) {
            case NEWEST -> page.addOrder(OrderItem.desc(TableFieldConstant.CREATE_TIME_UNDERLINE));
            case POPULAR -> page.addOrder(OrderItem.desc(ArticleFieldConstant.READ_COUNT_UNDERLINE));
            case RECOMMENDED -> page.addOrder(OrderItem.desc(ArticleFieldConstant.COMMENT_COUNT_UNDERLINE));
        }
        return page;
    }
}
