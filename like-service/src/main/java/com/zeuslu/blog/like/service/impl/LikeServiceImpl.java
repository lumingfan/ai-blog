package com.zeuslu.blog.like.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeuslu.blog.api.article.service.ArticleService;
import com.zeuslu.blog.api.like.domain.po.Like;
import com.zeuslu.blog.api.like.domain.vo.LikeResponseVO;
import com.zeuslu.blog.api.like.service.LikeService;
import com.zeuslu.blog.common.constant.RocketMqConstant;
import com.zeuslu.blog.common.enums.LikeTargetType;
import com.zeuslu.blog.common.event.LikeEvent;
import com.zeuslu.blog.common.util.SaTokenUtil;
import com.zeuslu.blog.like.mapper.LikeMapper;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lumingfan
 */
@Service
@RequiredArgsConstructor
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Like> implements LikeService {
    private final RocketMQTemplate rocketMQTemplate;

    @Autowired
    @Lazy
    private ArticleService articleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LikeResponseVO likeArticle(Long targetId) {
        Long userId = SaTokenUtil.getId();
        this.save(Like.builder()
                .userId(userId)
                .targetId(targetId)
                .targetType(LikeTargetType.ARTICLE)
                .build());

        // 发布点赞事件，由文章/通知服务监听处理
        rocketMQTemplate.convertAndSend(RocketMqConstant.TOPIC_LIKE_MESSAGE, new LikeEvent(targetId, LikeTargetType.ARTICLE, SaTokenUtil.getId(), true));


        // 获取该文章点赞数
        Integer likeCount = this.lambdaQuery()
                .eq(Like::getTargetId, targetId)
                .eq(Like::getTargetType, LikeTargetType.ARTICLE)
                .count().intValue();
        return LikeResponseVO.builder().isLiked(true).likeCount(likeCount).build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LikeResponseVO unLikeArticle(Long targetId) {
        Long userId = SaTokenUtil.getId();
        LambdaQueryWrapper<Like> wrapper = new LambdaQueryWrapper<Like>()
                .eq(Like::getUserId, userId)
                .eq(Like::getTargetType, LikeTargetType.ARTICLE)
                .eq(Like::getTargetId, targetId);
        this.remove(wrapper);

        // 更新文章表的点赞数
        rocketMQTemplate.convertAndSend(RocketMqConstant.TOPIC_LIKE_MESSAGE, new LikeEvent(targetId, LikeTargetType.ARTICLE, SaTokenUtil.getId(), false));
        // 获取该文章点赞数
        Integer likeCount = this.lambdaQuery()
                .eq(Like::getTargetId, targetId)
                .eq(Like::getTargetType, LikeTargetType.ARTICLE)
                .count().intValue();
        return LikeResponseVO.builder().isLiked(false).likeCount(likeCount).build();
    }

    @Override
    public Boolean isUserLikeArticle(Long userId, Long articleId) {
        return this.lambdaQuery()
                .eq(Like::getUserId, userId)
                .eq(Like::getTargetId, articleId)
                .eq(Like::getTargetType, LikeTargetType.ARTICLE)
                .exists();
    }

    @Override
    public Integer getUserLikesCount(Long userId) {
        Integer count = articleService.getLikeCountByUserId(userId);
        // TODO: 获取评论点赞数
        return count;
    }

    @Override
    public Integer getCommentLikeCount(Long commentId) {
        return this.lambdaQuery().eq(Like::getTargetType, LikeTargetType.COMMENT).eq(Like::getTargetId, commentId)
                .count().intValue();
    }

    @Override
    public Boolean isUserLikeComment(Long userId, Long commentId) {
        return this.lambdaQuery()
                .eq(Like::getUserId, userId)
                .eq(Like::getTargetId, commentId)
                .eq(Like::getTargetType, LikeTargetType.COMMENT)
                .exists();
    }

    @Override
    public Integer getCommentReplyLikeCount(Long commentReplyId) {
        return this.lambdaQuery().eq(Like::getTargetType, LikeTargetType.COMMENT_REPLY)
                .eq(Like::getTargetId, commentReplyId).count().intValue();
    }

    @Override
    public Boolean isUserLikeReply(Long userId, Long replyId) {
        return this.lambdaQuery()
                .eq(Like::getUserId, userId)
                .eq(Like::getTargetId, replyId)
                .eq(Like::getTargetType, LikeTargetType.COMMENT_REPLY)
                .exists();
    }


}
