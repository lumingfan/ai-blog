package com.zeuslu.blog.common.event;

import com.zeuslu.blog.common.enums.enums.LikeTargetType;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author lumingfan
 */
@Data
@AllArgsConstructor
public class LikeEvent {
    private Long targetId;
    private LikeTargetType targetType;
    private Boolean isLike;
}
