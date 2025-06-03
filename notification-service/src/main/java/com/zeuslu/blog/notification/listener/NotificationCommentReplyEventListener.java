package com.zeuslu.blog.notification.listener;

import com.zeuslu.blog.api.notification.service.NotificationService;
import com.zeuslu.blog.common.constant.RocketMqConstant;
import com.zeuslu.blog.common.event.CommentEvent;
import com.zeuslu.blog.common.event.CommentReplyEvent;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author lumingfan
 */
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(consumerGroup = RocketMqConstant.CONSUMER_GROUP_NOTIFICATION_COMMENT_REPLY_MESSAGE, topic = RocketMqConstant.TOPIC_COMMENT_REPLY_MESSAGE)
public class NotificationCommentReplyEventListener implements RocketMQListener<CommentReplyEvent> {
    private final NotificationService notificationService;

    @Override
    public void onMessage(CommentReplyEvent message) {
        notificationService.noticeCommentReply(message);
    }
}
