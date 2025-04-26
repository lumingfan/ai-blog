package com.zeuslu.blog.domain.po;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 帖子信息表
 * @author lumingfan
 * @TableName tb_post
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="tb_post")
@Data
public class Post extends Base {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 帖子标题
     */
    private String title;

    /**
     * 帖子摘要
     */
    private String summary;

    /**
     * 帖子封面
     */
    private String cover;

    /**
     * 帖子内容(Markdown)
     */
    private String content;

    /**
     * 帖子作者(外键关联tb_user)
     */
    private Long userId;

    /**
     * 分类id(外键关联tb_category)
     */
    private Long categoryId;

    /**
     * 帖子浏览次数
     */
    private Integer viewCount;

    /**
     * 帖子点赞次数
     */
    private Integer likeCount;

    /**
     * 状态: 0-草稿, 1-已发布, 2-已删除
     */
    private Integer status;


}