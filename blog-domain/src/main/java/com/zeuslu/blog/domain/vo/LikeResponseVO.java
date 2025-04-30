package com.zeuslu.blog.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lumingfan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "文章点赞返回对象")
public class LikeResponseVO {
    @Schema(description = "用户是否点赞")
    private Boolean isLiked;
    @Schema(description = "点赞数")
    private Integer likeCount;
}
