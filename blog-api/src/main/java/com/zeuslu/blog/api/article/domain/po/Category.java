package com.zeuslu.blog.api.article.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 帖子分类表
 * @author lumingfan
 * @TableName tb_category
 */
@TableName(value ="tb_category")
@Data
public class Category {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类排序值, 越小越靠前
     */
    private Integer sort;

    /**
     * 状态: 0-禁用, 1-启用
     */
    private Integer status;

    /**
     * 帖子数量
     */
    private Integer count;

    /**
     * 分类创建时间
     */
    private LocalDateTime createdAt;
}