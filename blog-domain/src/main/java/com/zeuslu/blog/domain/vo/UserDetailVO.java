package com.zeuslu.blog.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author lumingfan
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户详细信息VO")
public class UserDetailVO extends UserVO {
    @Schema(description = "文章数量")
    private Integer articleCount;

    @Schema(description = "粉丝数量")
    private Integer followerCount;

    @Schema(description = "关注数量")
    private Integer followingCount;

    @Schema(description = "获赞数量")
    private Integer totalLikes;

    @Schema(description = "标签")
    private List<String> tags;

    @Schema(description = "当前用户是否关注该用户")
    private Boolean isFollowed;
}
