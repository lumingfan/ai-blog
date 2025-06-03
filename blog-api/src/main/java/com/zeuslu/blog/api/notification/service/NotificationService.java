package com.zeuslu.blog.api.notification.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeuslu.blog.common.domain.PageResult;
import com.zeuslu.blog.api.notification.domain.dto.MarkNotificationReadDTO;
import com.zeuslu.blog.api.notification.domain.dto.NotificationPageQuery;
import com.zeuslu.blog.api.notification.domain.po.Notification;
import com.zeuslu.blog.api.notification.domain.vo.NotificationVO;
import com.zeuslu.blog.api.notification.domain.vo.UnreadNotificationCountVO;

public interface NotificationService extends IService<Notification> {
    PageResult<NotificationVO> getNotifications(NotificationPageQuery query);

    UnreadNotificationCountVO getUnreadCount();

    Boolean markAsRead(MarkNotificationReadDTO readDTO);

    void noticeLikeOnArticle(Long targetId, Long userId);

    void noticeFollowing(Long followerId, Long followingId);
}
