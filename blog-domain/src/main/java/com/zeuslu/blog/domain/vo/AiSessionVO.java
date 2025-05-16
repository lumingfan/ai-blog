package com.zeuslu.blog.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 获取AI会话列表VO
 * @author lumingfan
 */
@Data
@Schema(description = "获取AI会话列表VO")
public class AiSessionVO {
    @Schema(description = "会话ID")
    private Long id;
    @Schema(description = "用户ID")
    private Long userId;
    @Schema(description = "会话摘要")
    private String summary;
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
}
