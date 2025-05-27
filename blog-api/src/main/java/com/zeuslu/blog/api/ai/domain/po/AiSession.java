package com.zeuslu.blog.api.ai.domain.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * AI对话表
 * @author lumingfan
 */
@TableName(value ="tb_ai_session")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiSession {
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 用户id
     */
    private Long  userId;

    /**
     * 总结
     */
    private String summary;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField(fill= FieldFill.UPDATE)
    private LocalDateTime updatedAt;
}