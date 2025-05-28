package com.zeuslu.blog.api.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeuslu.blog.api.comment.domain.dto.CommentQueryParam;
import com.zeuslu.blog.api.comment.domain.dto.CommentReplyQueryParam;
import com.zeuslu.blog.api.comment.domain.dto.CreateCommentDTO;
import com.zeuslu.blog.api.comment.domain.dto.CreateCommentReplyDTO;
import com.zeuslu.blog.api.comment.domain.po.Comment;
import com.zeuslu.blog.api.comment.domain.vo.CommentReplyVO;
import com.zeuslu.blog.api.comment.domain.vo.CommentVO;
import com.zeuslu.blog.common.domain.PageResult;

/**
 * @author lumingfan
 */
public interface CommentService extends IService<Comment> {
    PageResult<CommentVO> getComments(CommentQueryParam param);

    PageResult<CommentReplyVO> getCommentReplies(CommentReplyQueryParam param);

    CommentVO addComment(CreateCommentDTO createCommentDTO);

    CommentReplyVO addCommentReply(CreateCommentReplyDTO createCommentReplyDTO);

    Boolean deleteComment(Long id);

    Boolean deleteCommentReply(Long id);
}
