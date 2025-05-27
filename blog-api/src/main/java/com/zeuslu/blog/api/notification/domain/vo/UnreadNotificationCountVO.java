package com.zeuslu.blog.api.notification.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @author lumingfan
 */
@Schema(description = "未读通知数量VO")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UnreadNotificationCountVO {
    public static final String LIKE = "LIKE";
    public static final String COMMENT = "COMMENT";
    public static final String FOLLOW = "FOLLOW";
    public static final String SYSTEM = "SYSTEM";

    @Schema(description = "未读通知数量")
    private Integer total;
    @Schema(description = "未读点赞数")
    private Integer like;
    @Schema(description = "未读评论数")
    private Integer comment;
    @Schema(description = "未读关注数")
    private Integer follow;
    @Schema(description = "未读系统通知数")
    private Integer system;
}
