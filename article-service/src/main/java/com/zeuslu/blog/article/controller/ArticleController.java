package com.zeuslu.blog.article.controller;

import com.zeuslu.blog.article.service.ArticleService;
import com.zeuslu.blog.common.annotation.Log;
import com.zeuslu.blog.common.domain.PageResult;
import com.zeuslu.blog.common.domain.Result;
import com.zeuslu.blog.domain.dto.ArticleDTO;
import com.zeuslu.blog.domain.dto.ArticlePageQuery;
import com.zeuslu.blog.domain.vo.ArticleItemVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author lumingfan
 */
@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
@Log
@Tag(name = "文章管理接口")
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/page")
    @Operation(summary = "获取文章列表接口")
    public Result<PageResult<ArticleItemVO>> pageArticle(ArticlePageQuery articlePageQuery) {
        return Result.ok(articleService.pageArticle(articlePageQuery));
    }

    @PostMapping
    @Operation(summary = "创建文章接口")
    public Result<Long> postArticle(@RequestBody ArticleDTO articleDTO) {
        return Result.ok(articleService.postArticle(articleDTO));
    }

}
