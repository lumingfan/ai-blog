package com.zeuslu.blog.notification.listener;

import com.zeuslu.blog.api.notification.service.NotificationService;
import com.zeuslu.blog.common.constant.RocketMqConstant;
import com.zeuslu.blog.common.event.CommentEvent;
import com.zeuslu.blog.common.event.FollowEvent;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author lumingfan
 */
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(consumerGroup = RocketMqConstant.CONSUMER_GROUP_NOTIFICATION_COMMENT_MESSAGE, topic = RocketMqConstant.TOPIC_COMMENT_MESSAGE)
public class NotificationCommentEventListener implements RocketMQListener<CommentEvent> {
    private final NotificationService notificationService;

    @Override
    public void onMessage(CommentEvent message) {
        notificationService.noticeComment(message);
    }
}
