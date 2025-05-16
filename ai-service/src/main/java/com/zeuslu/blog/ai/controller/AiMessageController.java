package com.zeuslu.blog.ai.controller;

import com.zeuslu.blog.ai.service.AiMessageService;
import com.zeuslu.blog.common.annotation.Log;
import com.zeuslu.blog.common.domain.PageQuery;
import com.zeuslu.blog.common.domain.PageResult;
import com.zeuslu.blog.common.domain.Result;
import com.zeuslu.blog.domain.dto.AiMessageDTO;
import com.zeuslu.blog.domain.vo.AiMessageVO;
import com.zeuslu.blog.domain.vo.AiSessionVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author lumingfan
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/ai/sessions")
@Tag(name = "AI对话管理接口")
@Log
public class AiMessageController {
    private final AiMessageService aiMessageService;

    @PostMapping("/messages")
    public Result<String> call(@RequestBody AiMessageDTO aiMessageDTO) {
        return Result.ok(aiMessageService.call(aiMessageDTO));
    }

    @PostMapping(value = "/messages/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Result<AiMessageVO>> stream(@RequestBody AiMessageDTO aiMessageDTO) {
        return aiMessageService.stream(aiMessageDTO).map(Result::ok);
    }

    @GetMapping
    public Result<PageResult<AiSessionVO>> getSessions(PageQuery query) {
        return Result.ok(aiMessageService.getSessions(query));
    }

    @GetMapping("/{id}")
    public Result<List<AiMessageVO>> getSessionMessages(@PathVariable Long id) {
        return Result.ok(aiMessageService.getSessionMessages(id));
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> deleteSession(@PathVariable Long id) {
        return Result.ok(aiMessageService.deleteSession(id));
    }
}
