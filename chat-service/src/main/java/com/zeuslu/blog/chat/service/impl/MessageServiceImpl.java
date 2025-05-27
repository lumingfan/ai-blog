package com.zeuslu.blog.chat.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeuslu.blog.chat.constant.WebSocketConstant;
import com.zeuslu.blog.chat.event.UpdateLastMessageEvent;
import com.zeuslu.blog.chat.mapper.MessageMapper;
import com.zeuslu.blog.api.chat.service.ConversationUserService;
import com.zeuslu.blog.api.chat.service.MessageService;
import com.zeuslu.blog.common.domain.PageQuery;
import com.zeuslu.blog.common.domain.PageResult;
import com.zeuslu.blog.common.enums.ChatMessageType;
import com.zeuslu.blog.common.errorcode.ChatErrorCode;
import com.zeuslu.blog.common.exception.CommonException;
import com.zeuslu.blog.common.util.SaTokenUtil;
import com.zeuslu.blog.api.chat.domain.dto.ChatMessageDTO;
import com.zeuslu.blog.api.chat.domain.po.ChatConversationUser;
import com.zeuslu.blog.api.chat.domain.po.ChatMessage;
import com.zeuslu.blog.api.chat.domain.vo.ChatMessageVO;
import com.zeuslu.blog.api.user.domain.vo.UserVO;
import com.zeuslu.blog.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author lumingfan
 */
@Service
@RequiredArgsConstructor
public class MessageServiceImpl extends ServiceImpl<MessageMapper, ChatMessage> implements MessageService {
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;
    private final ConversationUserService conversationUserService;
    private final static String DEFAULT_RECALL_MESSAGE = "%s撤回了一条消息";

    private final ApplicationEventPublisher eventPublisher;


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

        // 2. 更新对话表的最后一条消息
        eventPublisher.publishEvent(new UpdateLastMessageEvent(chatMessage.getId()));

        // 3. 返回消息VO
        ChatMessageVO chatMessageVO = fillMessageWithUser(chatMessage);

        // 4. 使用websocket进行消息推送实现实时聊天
        messagingTemplate.convertAndSend(WebSocketConstant.CHAT_TOPIC + "/" + chatMessage.getConversationId(), chatMessageVO);
        return chatMessageVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteMessage(Long id) {
        // 1. 检查删除的消息是否为当前用户发送的消息
        ChatMessage message = this.getById(id);
        if (message == null || !message.getSenderId().equals(SaTokenUtil.getId())) {
            throw new CommonException(ChatErrorCode.DELETE_MESSAGE_FAILED);
        }

        if (!this.removeById(id)) {
            throw new CommonException(ChatErrorCode.DELETE_MESSAGE_FAILED);
        }
        // 2. 更新对话表的最后一条消息为"{{用户}}撤回了一条消息"
        ChatMessageVO chatMessageVO = fillMessageWithUser(message);
        message.setId(null);
        message.setCreatedAt(null);
        message.setType(ChatMessageType.SYSTEM);
        message.setContent(DEFAULT_RECALL_MESSAGE.formatted(chatMessageVO.getSender().getNickname()));
        if (!this.save(message)) {
            throw new CommonException(ChatErrorCode.DELETE_MESSAGE_FAILED);
        }
        eventPublisher.publishEvent(new UpdateLastMessageEvent(message.getId()));

        // 3. 使用websocket进行消息推送实现实时聊天
        messagingTemplate.convertAndSend(WebSocketConstant.CHAT_TOPIC + "/" + message.getConversationId(), chatMessageVO);
        return true;
    }

    @Override
    public Integer getConversationMessageCount(Long conversationId) {
        return Math.toIntExact(this.lambdaQuery().eq(ChatMessage::getConversationId, conversationId).count());
    }

    @Override
    public Boolean closeConversation(Long id) {
        // 1. 确认当前用户在群聊id中
        if (!conversationUserService.lambdaQuery().eq(ChatConversationUser::getConversationId, id).eq(ChatConversationUser::getUserId, SaTokenUtil.getId()).exists()) {
            throw new CommonException(ChatErrorCode.USER_NOT_BELONG_TO_CHAT);
        }
        // 2. 更新用户离开群聊的时间
        return conversationUserService.lambdaUpdate().set(ChatConversationUser::getLastLeaveTime, LocalDateTime.now())
                .eq(ChatConversationUser::getConversationId, id)
                .eq(ChatConversationUser::getUserId, SaTokenUtil.getId()).update();
    }

    private ChatMessageVO fillMessageWithUser(ChatMessage message) {
        UserVO user = userService.getUserById(message.getSenderId());
        ChatMessageVO chatMessageVO = BeanUtil.copyProperties(message, ChatMessageVO.class);
        chatMessageVO.setSender(user);
        return chatMessageVO;
    }
}
