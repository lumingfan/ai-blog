package com.zeuslu.blog.article.controller;

import com.zeuslu.blog.api.article.service.ArticleService;
import com.zeuslu.blog.common.annotation.Log;
import com.zeuslu.blog.common.domain.PageResult;
import com.zeuslu.blog.common.domain.Result;
import com.zeuslu.blog.api.article.domain.dto.SaveArticleDTO;
import com.zeuslu.blog.api.article.domain.dto.ArticlePageQuery;
import com.zeuslu.blog.api.article.domain.vo.ArticleDetailVO;
import com.zeuslu.blog.api.article.domain.vo.ArticleItemVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    public Result<PageResult<ArticleItemVO>> pageArticle(@Valid ArticlePageQuery articlePageQuery) {
        return Result.ok(articleService.pageArticle(articlePageQuery));
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "获取文章详细信息")
    public Result<ArticleDetailVO> getArticleById(@PathVariable @NotNull Long id) throws NoResourceFoundException {
        return Result.ok(articleService.getArticleById(id));
    }

    @PostMapping
    @Operation(summary = "创建文章接口")
    public Result<Long> postArticle(@Valid @RequestBody SaveArticleDTO saveArticleDTO) {
        return Result.ok(articleService.postArticle(saveArticleDTO));
    }

    @PutMapping
    @Operation(summary = "更新文章接口")
    public Result<Long> updateArticle(@Valid @RequestBody SaveArticleDTO saveArticleDTO) {
        return Result.ok(articleService.updateArticle(saveArticleDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除文章接口")
    public Result<Boolean> deleteArticleById(@PathVariable @NotNull Long id) {
        return Result.ok(articleService.deleteArticleById(id));
    }

    @GetMapping("/like-count/{userId}")
    @Operation(summary = "获取用户点赞数量")
    public Result<Integer> likeArticleCount(@PathVariable @NotNull Long userId) {
        return Result.ok(articleService.getLikeCountByUserId(userId));
    }
}
