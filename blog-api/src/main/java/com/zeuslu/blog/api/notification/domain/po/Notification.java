package com.zeuslu.blog.api.notification.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zeuslu.blog.common.enums.NotificationType;
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
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_notification")
public class Notification {
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 通知类型
     */
    private NotificationType type;

    /**
     * 通知内容
     */
    private String content;

    /**
     * 是否已读
     */
    @TableField("is_read")
    private Boolean isRead;

    /**
     * 发送者id
     */
    private Long senderId;

    /**
     * 相关目标ID(文章ID,评论ID)
     */
    private Long targetId;

    /**
     * 相关目标标题(文章标题,评论内容)
     */
    private String targetTitle;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
