package com.zeuslu.blog.ai.service;

import com.zeuslu.blog.domain.dto.AiMessageDTO;
import reactor.core.publisher.Flux;

/**
 * @author lumingfan
 */
public interface AiMessageService {
    Flux<String> stream(AiMessageDTO aiMessageDTO);
    String call(AiMessageDTO aiMessageDTO);
}
