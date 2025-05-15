package com.zeuslu.blog.article.controller;

import com.zeuslu.blog.article.service.ArticleService;
import com.zeuslu.blog.common.annotation.Log;
import com.zeuslu.blog.common.domain.PageResult;
import com.zeuslu.blog.common.domain.Result;
import com.zeuslu.blog.domain.dto.ArticleDTO;
import com.zeuslu.blog.domain.vo.ArticleDetailVO;
import com.zeuslu.blog.domain.dto.ArticlePageQuery;
import com.zeuslu.blog.domain.vo.ArticleItemVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * @author lumingfan
 */
@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
@Log
@Tag(name = "文章管理接口")
@Validated
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/page")
    @Operation(summary = "获取文章列表接口")
    public Result<PageResult<ArticleItemVO>> pageArticle(ArticlePageQuery articlePageQuery) {
        return Result.ok(articleService.pageArticle(articlePageQuery));
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "获取文章详细信息")
    public Result<ArticleDetailVO> getArticleById(@PathVariable Long id) throws NoResourceFoundException {
        return Result.ok(articleService.getArticleById(id));
    }

    @PostMapping
    @Operation(summary = "创建文章接口")
    public Result<Long> postArticle(@RequestBody ArticleDTO articleDTO) {
        return Result.ok(articleService.postArticle(articleDTO));
    }

    @PutMapping
    @Operation(summary = "更新文章接口")
    public Result<Long> updateArticle(@RequestBody @Valid ArticleDTO articleDTO) {
        return Result.ok(articleService.updateArticle(articleDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除文章接口")
    public Result<Boolean> deleteArticleById(@PathVariable Long id) {
        return Result.ok(articleService.deleteArticleById(id));
    }
}
