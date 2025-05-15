package com.zeuslu.blog.like.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeuslu.blog.common.enums.LikeTargetType;
import com.zeuslu.blog.common.event.LikeEvent;
import com.zeuslu.blog.common.util.SaTokenUtil;
import com.zeuslu.blog.domain.po.Like;
import com.zeuslu.blog.domain.vo.LikeResponseVO;
import com.zeuslu.blog.like.mapper.LikeMapper;
import com.zeuslu.blog.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lumingfan
 */
@Service
@RequiredArgsConstructor
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Like> implements LikeService {
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LikeResponseVO likeArticle(Long targetId) {
        Long userId = SaTokenUtil.getId();
        this.save(Like.builder()
                .userId(userId)
                .targetId(targetId)
                .targetType(LikeTargetType.ARTICLE)
                .build());

        // 发布点赞事件，由文章服务监听处理
        eventPublisher.publishEvent(new LikeEvent(targetId, LikeTargetType.ARTICLE, true));


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
        eventPublisher.publishEvent(new LikeEvent(targetId, LikeTargetType.ARTICLE, false));
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
}
