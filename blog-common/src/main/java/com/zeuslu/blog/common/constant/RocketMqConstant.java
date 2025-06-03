package com.zeuslu.blog.common.constant;

/**
 * rocketmq常量
 * @author lumingfan
 */
public class RocketMqConstant {
    public static final String TOPIC_LIKE_MESSAGE = "like-message-topic";
    public static final String TOPIC_COMMENT_MESSAGE = "comment-message-topic";
    public static final String TOPIC_COMMENT_REPLY_MESSAGE = "comment-reply-message-topic";
    public static final String TOPIC_FOLLOW_MESSAGE = "follow-message-topic";

    public static final String CONSUMER_GROUP_ARTICLE_LIKE_MESSAGE = "article-like-message-consumer-group";
    public static final String CONSUMER_GROUP_NOTIFICATION_LIKE_MESSAGE = "notification-like-message-consumer-group";
    public static final String CONSUMER_GROUP_NOTIFICATION_FOLLOW_MESSAGE = "notification-follow-message-consumer-group";
    public static final String CONSUMER_GROUP_NOTIFICATION_COMMENT_MESSAGE = "notification-comment-message-consumer-group";
    public static final String CONSUMER_GROUP_NOTIFICATION_COMMENT_REPLY_MESSAGE = "notification-comment-reply-message-consumer-group";
}
