package com.zeuslu.blog.notification.listener;

import com.zeuslu.blog.api.notification.service.NotificationService;
import com.zeuslu.blog.common.constant.RocketMqConstant;
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
@RocketMQMessageListener(consumerGroup = RocketMqConstant.CONSUMER_GROUP_NOTIFICATION_FOLLOW_MESSAGE, topic = RocketMqConstant.TOPIC_FOLLOW_MESSAGE)
public class NotificationFollowEventListener implements RocketMQListener<FollowEvent> {
    private final NotificationService notificationService;

    @Override
    public void onMessage(FollowEvent message) {
        notificationService.noticeFollowing(message.getFollowerId(), message.getFollowingId());
    }
}
