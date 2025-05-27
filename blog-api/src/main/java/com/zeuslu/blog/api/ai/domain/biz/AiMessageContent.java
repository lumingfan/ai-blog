package com.zeuslu.blog.api.ai.domain.biz;

import com.zeuslu.blog.common.enums.AiMessageContentType;
import com.zeuslu.blog.common.enums.AiMessageRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author lumingfan
 */
@Schema(description = "AI消息内容")
@Data
public class AiMessageContent {
    @Schema(description = "消息类型")
    private AiMessageContentType type;
    @Schema(description = "消息内容")
    private String content;
    @Schema(description = "消息角色")
    private AiMessageRole role;
}
