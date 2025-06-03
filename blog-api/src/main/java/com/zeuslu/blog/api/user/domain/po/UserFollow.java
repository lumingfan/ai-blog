package com.zeuslu.blog.api.user.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户关注表
 * @author lumingfan
 * @TableName tb_user_follow
 */
@TableName(value ="tb_user_follow")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFollow {
    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 关注者id
     */
    private Long followerId;

    /**
     * 被关注者id
     */
    private Long followingId;

    /**
     * 关注时间
     */
    private LocalDateTime createdAt;
}