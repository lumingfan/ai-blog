package com.zeuslu.blog.chat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeuslu.blog.common.domain.PageQuery;
import com.zeuslu.blog.common.domain.PageResult;
import com.zeuslu.blog.domain.dto.ChatMessageDTO;
import com.zeuslu.blog.domain.po.ChatMessage;
import com.zeuslu.blog.domain.vo.ChatMessageVO;

public interface MessageService extends IService<ChatMessage> {
    PageResult<ChatMessageVO> getConversationMessages(Long id, PageQuery pageQuery);

    ChatMessageVO sendMessage(ChatMessageDTO chatMessageDTO);

    Boolean deleteMessage(Long id);

    Integer getConversationMessageCount(Long conversationId);

    Boolean closeConversation(Long id);
}
