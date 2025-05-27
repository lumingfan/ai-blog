package com.zeuslu.blog.api.notification.domain.vo;

import com.zeuslu.blog.api.user.domain.vo.UserVO;
import com.zeuslu.blog.common.enums.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author lumingfan
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "通知响应VO")
public class NotificationVO {
    @Schema(description = "通知ID")
    private Long id;
    @Schema(description = "通知类型")
    private NotificationType type;
    @Schema(description = "通知内容")
    private String content;
    @Schema(description = "是否已读")
    private Boolean isRead;
    @Schema(description = "发送者信息")
    private UserVO sender;
    @Schema(description = "相关目标ID(文章ID,评论ID)")
    private Long targetId;
    @Schema(description = "相关目标标题(文章标题,评论内容)")
    private String targetTitle;
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
}
