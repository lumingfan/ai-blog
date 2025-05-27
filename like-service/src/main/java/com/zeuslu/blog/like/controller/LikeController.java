package com.zeuslu.blog.like.controller;

import com.zeuslu.blog.api.like.domain.vo.LikeResponseVO;
import com.zeuslu.blog.api.like.service.LikeService;
import com.zeuslu.blog.common.annotation.Log;
import com.zeuslu.blog.common.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author lumingfan
 */
@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
@Tag(name = "点赞管理接口")
@Log
@Slf4j
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/articles/{id}")
    @Operation(summary = "点赞接口")
    public Result<LikeResponseVO> like(@PathVariable Long id) {
        return Result.ok(likeService.likeArticle(id));
    }

    @DeleteMapping("/articles/{id}")
    @Operation(summary = "取消点赞接口")
    public Result<LikeResponseVO> unlikeArticle(@PathVariable Long id) {
        return Result.ok(likeService.unLikeArticle(id));
    }

    @GetMapping("/{userId}")
    @Operation(summary = "获取用户点赞数")
    public Result<Integer> getUserLikesCount(@PathVariable Long userId) {
        return Result.ok(likeService.getUserLikesCount(userId));
    }
}
