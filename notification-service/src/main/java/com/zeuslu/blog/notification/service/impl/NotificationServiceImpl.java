package com.zeuslu.blog.notification.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeuslu.blog.api.article.domain.vo.ArticleDetailVO;
import com.zeuslu.blog.api.article.service.ArticleService;
import com.zeuslu.blog.api.notification.domain.dto.MarkNotificationReadDTO;
import com.zeuslu.blog.api.notification.domain.dto.NotificationPageQuery;
import com.zeuslu.blog.api.notification.domain.po.Notification;
import com.zeuslu.blog.api.notification.domain.vo.NotificationVO;
import com.zeuslu.blog.api.notification.domain.vo.UnreadNotificationCountVO;
import com.zeuslu.blog.api.notification.service.NotificationService;
import com.zeuslu.blog.api.user.domain.vo.UserVO;
import com.zeuslu.blog.api.user.service.UserService;
import com.zeuslu.blog.common.domain.PageResult;
import com.zeuslu.blog.common.enums.NotificationType;
import com.zeuslu.blog.common.util.SaTokenUtil;
import com.zeuslu.blog.notification.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lumingfan
 */
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements NotificationService {
    private final UserService userService;
    private final ArticleService articleService;

    @Override
    public PageResult<NotificationVO> getNotifications(NotificationPageQuery query) {
        Page<Notification> page = this.lambdaQuery()
                .eq(Notification::getUserId, SaTokenUtil.getId()).eq(Notification::getType, query.getType())
                .orderByDesc(Notification::getCreatedAt)
                .page(query.toPage());
        //TODO: 优化为批量查询
        return PageResult.of(page, notification -> {
            NotificationVO notificationVO = BeanUtil.copyProperties(notification, NotificationVO.class);
            notificationVO.setSender(BeanUtil.copyProperties(userService.getUserById(notification.getSenderId()), UserVO.class));
            return notificationVO;
        });
    }

    @Override
    public UnreadNotificationCountVO getUnreadCount() {
        Long userId = SaTokenUtil.getId();
        Map<String, Integer> map = baseMapper.countGroupByType(userId).stream().collect(
                Collectors.toMap(
                        elem -> elem.get("type").toString(),
                        elem -> ((Number) elem.get("count")).intValue()
                        )
        );
        return UnreadNotificationCountVO.builder()
                .total(map.values().stream().reduce(0, Integer::sum))
                .comment(map.getOrDefault(UnreadNotificationCountVO.COMMENT, 0))
                .follow(map.getOrDefault(UnreadNotificationCountVO.FOLLOW, 0))
                .like(map.getOrDefault(UnreadNotificationCountVO.LIKE, 0))
                .system(map.getOrDefault(UnreadNotificationCountVO.SYSTEM, 0))
                .build();
    }

    @Override
    public Boolean markAsRead(MarkNotificationReadDTO readDTO) {
        if (readDTO.getIds() == null || readDTO.getIds().isEmpty()) {
            return false; // 如果没有提供ID，则不进行任何操作
        }
        return this.lambdaUpdate()
                .eq(Notification::getUserId, SaTokenUtil.getId())
                .in(Notification::getId, readDTO.getIds())
                .set(Notification::getIsRead, true)
                .update();
    }

    @Override
    public void noticeLikeOnArticle(Long targetId, Long senderId) {
        ArticleDetailVO article = articleService.getArticleById(targetId);
        Long userId = article.getAuthor().getId();
        String title = article.getTitle();

        // 检查是否已经存在相同的通知(同一用户取消点赞后再次点赞时不重复发送通知)
        if (this.lambdaQuery().eq(Notification::getUserId, userId)
                .eq(Notification::getSenderId, senderId)
                .eq(Notification::getTargetId, targetId)
                .eq(Notification::getType, NotificationType.LIKE)
                .exists()) {
            return;
        }

        this.save(
                Notification.builder()
                        .content("用户 " + userService.getUserById(senderId).getUsername() + " 点赞了你的文章")
                        .type(NotificationType.LIKE)
                        .senderId(senderId)
                        .userId(userId)
                        .targetId(targetId)
                        .targetTitle(title)
                        .build()
        );
        // TODO: 增加websocket发送点赞通知
    }
}
