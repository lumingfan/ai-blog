package com.zeuslu.blog.comment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeuslu.blog.api.comment.domain.dto.CommentQueryParam;
import com.zeuslu.blog.api.comment.domain.po.Comment;
import com.zeuslu.blog.api.comment.domain.po.CommentReply;
import com.zeuslu.blog.api.comment.domain.vo.CommentVO;
import com.zeuslu.blog.api.comment.service.CommentService;
import com.zeuslu.blog.api.like.service.LikeService;
import com.zeuslu.blog.api.user.domain.vo.UserVO;
import com.zeuslu.blog.api.user.service.UserService;
import com.zeuslu.blog.comment.mapper.CommentMapper;
import com.zeuslu.blog.comment.service.CommentReplyService;
import com.zeuslu.blog.common.domain.PageResult;
import com.zeuslu.blog.common.util.SaTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    @Override
    public PageResult<CommentVO> getComments(CommentQueryParam param) {
        Page<Comment> page = this.lambdaQuery()
                .eq(Comment::getSubjectId, param.getSubjectId())
                .eq(Comment::getSubjectType, param.getSubjectType())
                .page(param.toPage());
        // 1. 获取评论用户
        List<Long> userIds = page.getRecords().stream().map(Comment::getUserId).toList();
        Map<Long, UserVO> userMap = userService.getBatchByIds(userIds).stream().collect(
                Collectors.toMap(
                        UserVO::getId,
                        user -> user
                )
        );

        // TODO: 优化为批量查询
        return PageResult.of(page, comment -> {
            CommentVO commentVO = BeanUtil.copyProperties(comment, CommentVO.class);
            commentVO.setUser(userMap.get(comment.getUserId()));
            // 2.获取评论点赞数和当前用户是否已点赞
            commentVO.setLikeCount(likeService.getCommentLikeCount(comment.getId()));
            commentVO.setIsLiked(likeService.isUserLikeComment(SaTokenUtil.getId(), comment.getId()));
            // 3. 获取评论回复数
            commentVO.setReplyCount(commentReplyService.lambdaQuery().eq(CommentReply::getParentId, comment.getId()).count().intValue());
            return commentVO;
        });
    }
}
