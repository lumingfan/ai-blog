package com.zeuslu.blog.chat.listener;

import com.zeuslu.blog.chat.event.UpdateLastMessageEvent;
import com.zeuslu.blog.api.chat.service.ConversationService;
import com.zeuslu.blog.api.chat.domain.po.ChatConversation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 监听更新最后一条消息事件
 * @author lumingfan
 */
@Component
@RequiredArgsConstructor
public class ConversationUpdateLastMessageListener {
    private final ConversationService conversationService;

    @EventListener
    public void handlerUpdateLastMessageEvent(UpdateLastMessageEvent event) {
        Long messageId = event.getLastMessageId();
        conversationService.lambdaUpdate().set(messageId != null, ChatConversation::getLastMessageId, messageId).update();
    }
}
