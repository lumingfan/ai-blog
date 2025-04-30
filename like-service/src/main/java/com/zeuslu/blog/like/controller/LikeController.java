package com.zeuslu.blog.like.controller;

import com.zeuslu.blog.common.annotation.Log;
import com.zeuslu.blog.common.domain.Result;
import com.zeuslu.blog.domain.vo.LikeResponseVO;
import com.zeuslu.blog.like.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author lumingfan
 */
@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
@Tag(name = "点赞管理接口")
@Log
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

}
