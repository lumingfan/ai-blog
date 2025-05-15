package com.zeuslu.blog.domain.dto;

import com.zeuslu.blog.common.domain.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author lumingfan
 */
@Schema(description = "AI会话查询参数")
public class AiSessionQuery extends PageQuery {
    private Long use;
}
