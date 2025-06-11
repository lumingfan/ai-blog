package com.zeuslu.blog.ai.strategy;

import com.zeuslu.blog.api.ai.domain.dto.AiMessageDTO;
import com.zeuslu.blog.common.enums.AiModelEnums;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author lumingfan
 */
public interface ChatModelStrategy {
    List<AiModelEnums> getTypes();
    String call(AiMessageDTO aiMessageDTO);
    Flux<String> stream(AiMessageDTO aiMessageDTO);
}
