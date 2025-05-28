package com.zeuslu.blog.comment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeuslu.blog.api.comment.domain.po.CommentReply;
import com.zeuslu.blog.comment.mapper.CommentReplyMapper;
import com.zeuslu.blog.comment.service.CommentReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author lumingfan
 */
@Service
@RequiredArgsConstructor
public class CommentReplyServiceImpl extends ServiceImpl<CommentReplyMapper, CommentReply> implements CommentReplyService {
}
