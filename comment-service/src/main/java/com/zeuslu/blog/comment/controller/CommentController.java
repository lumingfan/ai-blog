package com.zeuslu.blog.comment.controller;

import com.zeuslu.blog.api.comment.domain.dto.CommentQueryParam;
import com.zeuslu.blog.api.comment.domain.dto.CommentReplyQueryParam;
import com.zeuslu.blog.api.comment.domain.dto.CreateCommentDTO;
import com.zeuslu.blog.api.comment.domain.dto.CreateCommentReplyDTO;
import com.zeuslu.blog.api.comment.domain.vo.CommentReplyVO;
import com.zeuslu.blog.api.comment.domain.vo.CommentVO;
import com.zeuslu.blog.api.comment.service.CommentService;
import com.zeuslu.blog.common.annotation.Log;
import com.zeuslu.blog.common.domain.PageResult;
import com.zeuslu.blog.common.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author lumingfan
 */
@Tag(name = "评论相关接口")
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Log
public class CommentController {
    private final CommentService commentService;
    @Operation(summary = "获取评论列表")
    @GetMapping
    public Result<PageResult<CommentVO>> getComments(CommentQueryParam param) {
        return Result.ok(commentService.getComments(param));
    }

    @Operation(summary = "获取评论回复列表")
    @GetMapping("/replies")
    public Result<PageResult<CommentReplyVO>> getCommentReplies(CommentReplyQueryParam param) {
        return Result.ok(commentService.getCommentReplies(param));
    }

    @Operation(summary = "新增评论")
    @PostMapping
    public Result<CommentVO> addComment(@RequestBody CreateCommentDTO createCommentDTO) {
        return Result.ok(commentService.addComment(createCommentDTO));
    }

    @Operation(summary = "新增评论回复")
    @PostMapping("/replies")
    public Result<CommentReplyVO> addCommentReply(@RequestBody CreateCommentReplyDTO createCommentReplyDTO) {
        return Result.ok(commentService.addCommentReply(createCommentReplyDTO));
    }

    @Operation(summary = "删除评论")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteComment(@PathVariable Long id) {
        return Result.ok(commentService.deleteComment(id));
    }

    @Operation(summary = "删除评论回复")
    @DeleteMapping("/replies/{id}")
    public Result<Boolean> deleteCommentReply(@PathVariable Long id) {
        return Result.ok(commentService.deleteCommentReply(id));
    }
}
