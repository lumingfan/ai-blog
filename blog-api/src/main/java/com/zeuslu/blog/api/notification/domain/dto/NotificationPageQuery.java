package com.zeuslu.blog.api.notification.domain.dto;

import com.zeuslu.blog.common.domain.PageQuery;
import com.zeuslu.blog.common.enums.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lumingfan
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "通知分页条件查询")
public class NotificationPageQuery extends PageQuery {
    @Schema(description = "通知类型")
    private NotificationType type;
}
