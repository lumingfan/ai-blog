package com.zeuslu.blog.api.article.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zeuslu.blog.common.domain.Base;
import lombok.*;

/**
 * 文章表
 * @author lumingfan
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="tb_article")
@Data
public class Article extends Base {
    /**
     * 文章主键id
     */
    @TableId
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 文章摘要(50字以内)
     */
    private String summary;

    /**
     * 文章封面
     */
    private String coverImage;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 是否是草稿
     */
    @TableField(value = "is_draft")
    private Boolean draft;

    /**
     * 文章作者id
     */
    private Long authorId;

    /**
     * 文章分类id
     */
    private Long categoryId;

    /**
     * 文章阅读数
     */
    private Integer readCount;

    /**
     * 文章点赞数
     */
    private Integer likeCount;

    /**
     * 文章评论数
     */
    private Integer commentCount;
}