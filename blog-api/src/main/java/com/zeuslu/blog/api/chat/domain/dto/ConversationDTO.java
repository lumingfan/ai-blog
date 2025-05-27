package com.zeuslu.blog.api.chat.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * @author lumingfan
 */
@Data
@Schema(description = "创建对话DTO")
public class ConversationDTO {
    @Schema(description = "对话参与用户id")
    @NotEmpty
    private List<Long> participantIds;
}
