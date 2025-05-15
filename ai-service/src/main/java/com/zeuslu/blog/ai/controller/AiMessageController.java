package com.zeuslu.blog.ai.controller;

import com.zeuslu.blog.ai.service.AiMessageService;
import com.zeuslu.blog.common.domain.Result;
import com.zeuslu.blog.domain.dto.AiMessageDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author lumingfan
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/ai/sessions")
@Tag(name = "AI对话管理接口")
public class AiMessageController {
    private final AiMessageService aiMessageService;

    @PostMapping("/messages")
    public Result<String> call(@RequestBody AiMessageDTO aiMessageDTO) {
        return Result.ok(aiMessageService.call(aiMessageDTO));
    }

    @PostMapping(value = "/messages/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Result<String>> stream(@RequestBody AiMessageDTO aiMessageDTO) {
        return aiMessageService.stream(aiMessageDTO).map(Result::ok);
    }

}
