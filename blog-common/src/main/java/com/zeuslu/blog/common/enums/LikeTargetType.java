package com.zeuslu.blog.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @author lumingfan
 */
@Getter
public enum LikeTargetType {
    ARTICLE(1, "文章"),
    COMMENT(2, "评论"),
    COMMENT_REPLY(3, "评论回复")
    ;

    @EnumValue
    private final int code;
    private final String desc;

    LikeTargetType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
