package com.zeuslu.blog.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zeuslu.blog.common.enums.enums.LikeTargetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 点赞模块
 * @author lumingfan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_like")
public class Like {
    @TableId
    private Long id;
    private Long userId;
    private Long targetId;
    private LikeTargetType targetType;
    private LocalDateTime createdAt;
}
