package com.zeuslu.blog.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关注事件
 * @author lumingfan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowEvent {
    // 关注者ID
    private Long followerId;
    // 被关注者ID
    private Long followingId;
}
