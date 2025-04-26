package com.zeuslu.blog.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 标签表
 * @author lumingfan
 * @TableName tb_tag
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="tb_tag")
@Data
public class Tag extends Base {
    /**
     * 标签ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 帖子数量, 通过聚合函数计算
     */
    @TableField(exist = false)
    private Integer count;
}