package com.zeuslu.blog.comment.controller;

import com.zeuslu.blog.api.comment.domain.dto.CommentQueryParam;
import com.zeuslu.blog.api.comment.domain.vo.CommentVO;
import com.zeuslu.blog.api.comment.service.CommentService;
import com.zeuslu.blog.common.domain.PageResult;
import com.zeuslu.blog.common.domain.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lumingfan
 */
@Tag(name = "评论相关接口")
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @GetMapping
    public Result<PageResult<CommentVO>> getComments(CommentQueryParam param) {
        return Result.ok(commentService.getComments(param));
    }

}
