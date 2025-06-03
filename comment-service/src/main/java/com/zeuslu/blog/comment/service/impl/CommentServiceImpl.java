package com.zeuslu.blog.comment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeuslu.blog.api.comment.domain.dto.CommentQueryParam;
import com.zeuslu.blog.api.comment.domain.dto.CommentReplyQueryParam;
import com.zeuslu.blog.api.comment.domain.dto.CreateCommentDTO;
import com.zeuslu.blog.api.comment.domain.dto.CreateCommentReplyDTO;
import com.zeuslu.blog.api.comment.domain.po.Comment;
import com.zeuslu.blog.api.comment.domain.po.CommentReply;
import com.zeuslu.blog.api.comment.domain.vo.CommentReplyVO;
import com.zeuslu.blog.api.comment.domain.vo.CommentVO;
import com.zeuslu.blog.api.comment.service.CommentService;
import com.zeuslu.blog.api.like.service.LikeService;
import com.zeuslu.blog.api.user.domain.vo.UserVO;
import com.zeuslu.blog.api.user.service.UserService;
import com.zeuslu.blog.comment.mapper.CommentMapper;
import com.zeuslu.blog.comment.service.CommentReplyService;
import com.zeuslu.blog.common.constant.RocketMqConstant;
import com.zeuslu.blog.common.domain.PageResult;
import com.zeuslu.blog.common.event.CommentEvent;
import com.zeuslu.blog.common.event.CommentReplyEvent;
import com.zeuslu.blog.common.util.SaTokenUtil;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lumingfan
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    private final UserService userService;
    private final LikeService likeService;
    private final CommentReplyService commentReplyService;
    private final RocketMQTemplate rocketMQTemplate;

    @Override
    public PageResult<CommentVO> getComments(CommentQueryParam param) {
        Page<Comment> page = this.lambdaQuery()
                .eq(Comment::getSubjectId, param.getSubjectId())
                .eq(Comment::getSubjectType, param.getSubjectType())
                .page(param.toPage());
        // 1. 获取评论用户
        List<Long> userIds = page.getRecords().stream().map(Comment::getUserId).toList();
        Map<Long, UserVO> userMap = getUserMap(userIds);

        // TODO: 优化为批量查询
        return PageResult.of(page, comment -> {
            CommentVO commentVO = BeanUtil.copyProperties(comment, CommentVO.class);
            commentVO.setUser(userMap.get(comment.getUserId()));
            // 2.获取评论点赞数和当前用户是否已点赞
            commentVO.setLikeCount(likeService.getCommentLikeCount(comment.getId()));
            commentVO.setIsLiked(likeService.isUserLikeComment(SaTokenUtil.getId(), comment.getId()));
            // 3. 获取评论回复数
            commentVO.setReplyCount(commentReplyService.lambdaQuery().eq(CommentReply::getCommentId, comment.getId()).count().intValue());
            return commentVO;
        });
    }

    @Override
    public PageResult<CommentReplyVO> getCommentReplies(CommentReplyQueryParam param) {
        Page<CommentReply> page = commentReplyService.lambdaQuery()
                .eq(CommentReply::getCommentId, param.getCommentId())
                .page(param.toPage());
        // 1. 获取评论用户
        List<Long> userIds = page.getRecords().stream().map(CommentReply::getUserId).toList();
        Map<Long, UserVO> userMap = getUserMap(userIds);

        // TODO: 优化为批量查询
        return PageResult.of(page, reply -> {
            CommentReplyVO commentReplyVO = BeanUtil.copyProperties(reply, CommentReplyVO.class);
            commentReplyVO.setUser(userMap.get(reply.getUserId()));
            // 2.获取评论点赞数和当前用户是否已点赞
            commentReplyVO.setLikeCount(likeService.getCommentReplyLikeCount(reply.getId()));
            commentReplyVO.setIsLiked(likeService.isUserLikeReply(SaTokenUtil.getId(), reply.getId()));
            // 3. 获取被@的用户
            if (reply.getAtUserId() != null) {
                commentReplyVO.setAtUser(userService.getUserById(reply.getAtUserId()));
            }
            return commentReplyVO;
        });
    }

    @Override
    public CommentVO addComment(CreateCommentDTO createCommentDTO) {
        Comment comment = BeanUtil.copyProperties(createCommentDTO, Comment.class);
        comment.setUserId(SaTokenUtil.getId());
        // 手动设置创建时间为了后面少一次sql查询
        comment.setCreatedAt(LocalDateTime.now());
        if (!this.save(comment)) {
            return null;
        }
        // TODO: 异步编排优化
        CommentVO commentVO = BeanUtil.copyProperties(comment, CommentVO.class);
        commentVO.setUser(userService.getUserById(comment.getUserId()));
        // 发送评论通知
        rocketMQTemplate.convertAndSend(
                RocketMqConstant.TOPIC_COMMENT_MESSAGE,
                CommentEvent.builder()
                        .targetId(comment.getSubjectId())
                        .type(comment.getSubjectType())
                        .commenterId(comment.getUserId())
                        .content(comment.getContent())
                        .build());
        return commentVO;
    }

    @Override
    public CommentReplyVO addCommentReply(CreateCommentReplyDTO createCommentReplyDTO) {
        CommentReply commentReply = BeanUtil.copyProperties(createCommentReplyDTO, CommentReply.class);
        commentReply.setUserId(SaTokenUtil.getId());
        commentReply.setCreatedAt(LocalDateTime.now());
        if (!commentReplyService.save(commentReply)) {
            return null;
        }
        CommentReplyVO commentReplyVO = BeanUtil.copyProperties(commentReply, CommentReplyVO.class);
        commentReplyVO.setUser(userService.getUserById(commentReply.getUserId()));
        if (createCommentReplyDTO.getAtUserId() != null) {
            commentReplyVO.setAtUser(userService.getUserById(commentReply.getAtUserId()));
        }
        // 发送评论回复通知
        rocketMQTemplate.convertAndSend(
                RocketMqConstant.TOPIC_COMMENT_REPLY_MESSAGE,
                CommentReplyEvent.builder()
                        .commentId(commentReply.getCommentId())
                        .commenterId(commentReply.getUserId())
                        .atUserId(commentReply.getAtUserId())
                        .content(commentReply.getContent())
                        .build()
        );
        return commentReplyVO;
    }

    @Override
    @Transactional
    public Boolean deleteComment(Long id) {
        // 1. 删除评论
        if (!this.removeById(id)) {
            return false;
        }
        // 2. 删除评论下的回复
        // TODO: 优化为消息队列异步删除
        return commentReplyService.lambdaUpdate()
                .eq(CommentReply::getCommentId, id)
                .remove();
    }

    @Override
    public Boolean deleteCommentReply(Long id) {
        return commentReplyService.removeById(id);
    }

    private Map<Long, UserVO> getUserMap(List<Long> userIds) {
        return userService.getBatchByIds(userIds).stream().collect(
                Collectors.toMap(
                        UserVO::getId,
                        user -> user
                )
        );
    }

}
