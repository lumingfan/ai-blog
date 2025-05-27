package com.zeuslu.blog.article.listener;

import com.zeuslu.blog.api.article.service.ArticleService;
import com.zeuslu.blog.common.enums.LikeTargetType;
import com.zeuslu.blog.common.event.LikeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 监听点赞事件
 * @author lumingfan
 */
@Component
@RequiredArgsConstructor
public class ArticleLikeEventListener {
    private final ArticleService articleService;

    @EventListener
    public void handleLikeEvent(LikeEvent event) {
        if (event.getTargetType() == LikeTargetType.ARTICLE) {
            if (event.getIsLike()) {
                articleService.incrementLikeCount(event.getTargetId());
            } else {
                articleService.decrementLikeCount(event.getTargetId());
            }
        }
    }
}