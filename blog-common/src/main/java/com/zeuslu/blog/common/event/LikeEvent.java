package com.zeuslu.blog.common.event;

import com.zeuslu.blog.common.enums.LikeTargetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lumingfan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeEvent {
    // 点赞目标ID
    private Long targetId;
    // 点赞目标类型(评论, 文章)
    private LikeTargetType targetType;
    // 点赞用户ID
    private Long userId;
    // 点赞/取消点赞
    private Boolean isLike;
}
