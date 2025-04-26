package com.zeuslu.blog.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 帖子标签映射表
 * @author lumingfan
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="tb_post_tag")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostTag extends Base {
    /**
     * 主键ID
     */
    @TableId(type= IdType.AUTO)
    private Long id;

    /**
     * 帖子ID(外键关联tb_post)
     */
    private Long postId;

    /**
     * 标签ID(外键关联tb_tag)
     */
    private Long tagId;
}
