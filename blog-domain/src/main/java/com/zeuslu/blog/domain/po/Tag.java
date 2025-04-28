package com.zeuslu.blog.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 标签表
 * @author lumingfan
 * @TableName tb_tag
 */
@TableName(value ="tb_tag")
@Data
public class Tag {
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
     * 创建时间
     */
    private Date createdAt;
}