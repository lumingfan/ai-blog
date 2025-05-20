package com.zeuslu.blog.chat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeuslu.blog.common.domain.PageQuery;
import com.zeuslu.blog.common.domain.PageResult;
import com.zeuslu.blog.domain.dto.ConversationDTO;
import com.zeuslu.blog.domain.po.ChatConversation;
import com.zeuslu.blog.domain.vo.ConversationVO;

/**
 * @author lumingfan
 */
public interface ConversationService extends IService<ChatConversation> {
    PageResult<ConversationVO> getConversations(PageQuery pageQuery);

    ConversationVO createConversation(ConversationDTO conversationDTO);

    Boolean deleteConversation(Long id);
}
