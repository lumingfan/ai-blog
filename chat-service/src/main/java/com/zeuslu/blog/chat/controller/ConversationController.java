package com.zeuslu.blog.chat.controller;

import com.zeuslu.blog.chat.service.ConversationService;
import com.zeuslu.blog.chat.service.MessageService;
import com.zeuslu.blog.common.annotation.Log;
import com.zeuslu.blog.common.domain.PageQuery;
import com.zeuslu.blog.common.domain.PageResult;
import com.zeuslu.blog.common.domain.Result;
import com.zeuslu.blog.domain.dto.ChatMessageDTO;
import com.zeuslu.blog.domain.dto.ConversationDTO;
import com.zeuslu.blog.domain.vo.ChatMessageVO;
import com.zeuslu.blog.domain.vo.ConversationVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author lumingfan
 */
@Tag(name = "对话管理接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/conversations")
@Log
public class ConversationController {

    private final ConversationService conversationService;
    private final MessageService messageService;

    @Operation(summary = "获取对话列表")
    @GetMapping
    public Result<PageResult<ConversationVO>> getConversations(PageQuery pageQuery) {
        return Result.ok(conversationService.getConversations(pageQuery));
    }

    @Operation(summary = "创建对话")
    @PostMapping
    public Result<ConversationVO> createConversation(@RequestBody ConversationDTO conversationDTO) {
        return Result.ok(conversationService.createConversation(conversationDTO));
    }

    @Operation(summary = "删除对话")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteConversation(@PathVariable Long id) {
        return Result.ok(conversationService.deleteConversation(id));
    }

    @Operation(summary = "获取对话中的消息列表")
    @GetMapping("/{id}/messages")
    public Result<PageResult<ChatMessageVO>> getConversationMessages(@PathVariable Long id, PageQuery pageQuery) {
        return Result.ok(messageService.getConversationMessages(id, pageQuery));
    }

    @Operation(summary = "发送消息")
    @PostMapping("/messages")
    public Result<ChatMessageVO> sendMessage(@RequestBody ChatMessageDTO chatMessageDTO) {
        return Result.ok(messageService.sendMessage(chatMessageDTO));
    }

    @Operation(summary = "撤回消息")
    @DeleteMapping("/messages/{id}")
    public Result<Boolean> deleteMessage(@PathVariable Long id) {
        return Result.ok(messageService.deleteMessage(id));
    }

    @Operation(summary = "获取消息总数")
    @GetMapping("/{id}/messages/count")
    public Result<Integer> getConversationMessageCount(@PathVariable Long id) {
        return Result.ok(messageService.getConversationMessageCount(id));
    }

    @Operation(summary = "用户关闭聊天窗口")
    @PutMapping("/{id}/leave")
    public Result<Boolean> closeConversation(@PathVariable Long id) {
        return Result.ok(messageService.closeConversation(id));
    }
}
