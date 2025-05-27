package com.zeuslu.blog.api.notification.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author lumingfan
 */
@Schema(description = "标记通知为已读DTO")
@Data
public class MarkNotificationReadDTO {
    private List<Long> ids;
}
