package com.zeuslu.blog.ai.service.impl;

import com.zeuslu.blog.ai.factory.ChatModelStrategyFactory;
import com.zeuslu.blog.ai.service.AiMessageService;
import com.zeuslu.blog.domain.dto.AiMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @author lumingfan
 */
@Service
@RequiredArgsConstructor
public class AiMessageServiceImpl implements AiMessageService {
    private final ChatModelStrategyFactory chatModelFactory;
    @Override
    public Flux<String> stream(AiMessageDTO aiMessageDTO) {
        return chatModelFactory.getStrategy(aiMessageDTO.getModel().getModel()).stream(aiMessageDTO);
    }

    @Override
    public String call(AiMessageDTO aiMessageDTO) {
        return chatModelFactory.getStrategy(aiMessageDTO.getModel().getModel()).call(aiMessageDTO);
    }
}
