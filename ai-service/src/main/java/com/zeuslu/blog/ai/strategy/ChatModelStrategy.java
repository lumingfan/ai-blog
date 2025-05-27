package com.zeuslu.blog.ai.strategy;

import com.zeuslu.blog.api.ai.domain.dto.AiMessageDTO;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author lumingfan
 */
public interface ChatModelStrategy {
    List<String> getTypes();
    String call(AiMessageDTO aiMessageDTO);
    Flux<String> stream(AiMessageDTO aiMessageDTO);
}
