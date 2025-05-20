package com.zeuslu.blog.chat.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeuslu.blog.chat.constant.WebSocketConstant;
import com.zeuslu.blog.chat.mapper.MessageMapper;
import com.zeuslu.blog.chat.service.MessageService;
import com.zeuslu.blog.common.domain.PageQuery;
import com.zeuslu.blog.common.domain.PageResult;
import com.zeuslu.blog.common.errorcode.ChatErrorCode;
import com.zeuslu.blog.common.exception.CommonException;
import com.zeuslu.blog.common.util.SaTokenUtil;
import com.zeuslu.blog.domain.dto.ChatMessageDTO;
import com.zeuslu.blog.domain.po.ChatMessage;
import com.zeuslu.blog.domain.vo.ChatMessageVO;
import com.zeuslu.blog.domain.vo.UserVO;
import com.zeuslu.blog.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * @author lumingfan
 */
@Service
@RequiredArgsConstructor
public class MessageServiceImpl extends ServiceImpl<MessageMapper, ChatMessage> implements MessageService {
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;
    private final SimpMessagingTemplate simpMessagingTemplate;


    @Override
    public PageResult<ChatMessageVO> getConversationMessages(Long id, PageQuery pageQuery) {
        Page<ChatMessage> page = this.lambdaQuery()
                .eq(ChatMessage::getConversationId, id)
                .orderBy(true, true, ChatMessage::getCreatedAt)
                .page(pageQuery.toPage());
        // TODO: 优化为批量查询
        return PageResult.of(page, chatMessage -> {
            Long senderId = chatMessage.getSenderId();
            UserVO sender = userService.getUserById(senderId);
            ChatMessageVO chatMessageVO = BeanUtil.copyProperties(chatMessage, ChatMessageVO.class);
            chatMessageVO.setSender(sender);
            return chatMessageVO;
        });
    }

    @Override
    public ChatMessageVO sendMessage(ChatMessageDTO chatMessageDTO) {
        // 1. 保存消息到消息表
        ChatMessage chatMessage = BeanUtil.copyProperties(chatMessageDTO, ChatMessage.class);
        chatMessage.setSenderId(SaTokenUtil.getId());
        if (!this.save(chatMessage)) {
            throw new CommonException(ChatErrorCode.SEND_MESSAGE_FAILED);
        }
        chatMessage = this.getById(chatMessage.getId());

        // 2. TODO:更新对话表的最后一条消息


        // 3. TODO:更新对话用户管理表的用户未读消息数


        // 4. 返回消息VO
        UserVO user = userService.getUserById(chatMessage.getSenderId());
        ChatMessageVO chatMessageVO = BeanUtil.copyProperties(chatMessage, ChatMessageVO.class);
        chatMessageVO.setSender(user);

        // 5. TODO: 使用websocket进行消息推送实现实时聊天
        messagingTemplate.convertAndSend(WebSocketConstant.CHAT_TOPIC + "/" + chatMessage.getConversationId(), chatMessageVO);
        return chatMessageVO;
    }

    @Override
    public Boolean deleteMessage(Long id) {
        if (!this.removeById(id)) {
            throw new CommonException(ChatErrorCode.DELETE_MESSAGE_FAILED);
        }
        // 2. TODO:更新对话表的最后一条消息为"用户撤回了一条消息"

        // 3. TODO:更新对话用户管理表的用户未读消息数

        // 4. TODO: 使用websocket进行消息推送实现实时聊天
        return true;
    }

    @Override
    public Integer getConversationMessageCount(Long conversationId) {
        return Math.toIntExact(this.lambdaQuery().eq(ChatMessage::getConversationId, conversationId).count());
    }
}
