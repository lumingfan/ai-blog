package com.zeuslu.blog.notification.listener;

import com.zeuslu.blog.common.enums.LikeTargetType;
import com.zeuslu.blog.common.event.LikeEvent;
import com.zeuslu.blog.api.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author lumingfan
 */
@Component
@RequiredArgsConstructor
public class LikeEventListener {
    private final NotificationService notificationService;

    @EventListener
    public void handleLikeEvent(LikeEvent event) {
        if (event.getTargetType() == LikeTargetType.ARTICLE) {
            if (event.getIsLike()) {
                notificationService.noticeLikeOnArticle(event.getTargetId(), event.getUserId());
            }
        }
    }

}
