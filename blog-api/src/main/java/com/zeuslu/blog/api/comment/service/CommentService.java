package com.zeuslu.blog.api.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeuslu.blog.api.comment.domain.dto.CommentQueryParam;
import com.zeuslu.blog.api.comment.domain.po.Comment;
import com.zeuslu.blog.api.comment.domain.vo.CommentVO;
import com.zeuslu.blog.common.domain.PageResult;

/**
 * @author lumingfan
 */
public interface CommentService extends IService<Comment> {
    PageResult<CommentVO> getComments(CommentQueryParam param);
}
