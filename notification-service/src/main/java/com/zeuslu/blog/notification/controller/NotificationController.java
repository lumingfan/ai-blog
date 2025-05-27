package com.zeuslu.blog.notification.controller;

import com.zeuslu.blog.api.notification.domain.dto.MarkNotificationReadDTO;
import com.zeuslu.blog.api.notification.domain.dto.NotificationPageQuery;
import com.zeuslu.blog.api.notification.domain.vo.NotificationVO;
import com.zeuslu.blog.api.notification.domain.vo.UnreadNotificationCountVO;
import com.zeuslu.blog.api.notification.service.NotificationService;
import com.zeuslu.blog.common.annotation.Log;
import com.zeuslu.blog.common.domain.PageResult;
import com.zeuslu.blog.common.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author lumingfan
 */
@Tag(name = "通知相关接口")
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@Validated
@Log
public class NotificationController {
    private final NotificationService notificationService;

    @Operation(summary = "获取通知列表接口")
    @GetMapping
    public Result<PageResult<NotificationVO>> getNotifications(@Valid NotificationPageQuery query) {
        return Result.ok(notificationService.getNotifications(query));
    }

    @Operation(summary = "获取未读通知数量")
    @GetMapping("/unread-count")
    public Result<UnreadNotificationCountVO> getUnreadCount() {
        return Result.ok(notificationService.getUnreadCount());
    }

    @Operation(summary = "标记通知为已读")
    @PutMapping("/read-status")
    public Result<Boolean> markAsRead(@RequestBody MarkNotificationReadDTO readDTO) {
        return Result.ok(notificationService.markAsRead(readDTO));
    }


}
