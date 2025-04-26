package com.zeuslu.blog.domain.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lumingfan
 * 实体类通用字段
 */
@Data
public class Base {
    /**
     * 创建时间
     */
    @TableField(fill= FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 修改时间
     */
    @TableField(fill=FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    /**
     * 删除标识 0：未删除 1：已删除
     */
    @TableField(value="is_deleted", fill=FieldFill.INSERT)
    @TableLogic
    private Integer deleted;
}
