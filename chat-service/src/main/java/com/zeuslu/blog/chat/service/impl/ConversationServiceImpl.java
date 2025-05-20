package com.zeuslu.blog.chat.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeuslu.blog.chat.mapper.ConversationMapper;
import com.zeuslu.blog.chat.mapper.ConversationUserMapper;
import com.zeuslu.blog.chat.service.ConversationService;
import com.zeuslu.blog.chat.service.MessageService;
import com.zeuslu.blog.common.domain.PageQuery;
import com.zeuslu.blog.common.domain.PageResult;
import com.zeuslu.blog.common.errorcode.ChatErrorCode;
import com.zeuslu.blog.common.exception.CommonException;
import com.zeuslu.blog.common.util.SaTokenUtil;
import com.zeuslu.blog.domain.dto.ConversationDTO;
import com.zeuslu.blog.domain.po.ChatConversation;
import com.zeuslu.blog.domain.po.ChatConversationUser;
import com.zeuslu.blog.domain.po.ChatMessage;
import com.zeuslu.blog.domain.vo.ConversationVO;
import com.zeuslu.blog.domain.vo.UserVO;
import com.zeuslu.blog.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author lumingfan
 */
@Service
@RequiredArgsConstructor
public class ConversationServiceImpl extends ServiceImpl<ConversationMapper, ChatConversation> implements ConversationService {
    private final ConversationUserMapper conversationUserMapper;
    private final UserService userService;
    private final MessageService messageService;

    @Override
    public PageResult<ConversationVO> getConversations(PageQuery pageQuery) {
        // 1. 获取对话列表
        Long userId = SaTokenUtil.getId();
        Page<ChatConversation> page = pageQuery.toPage();
        page = this.baseMapper.getConversationsByUserId(page, userId);


        List<Long> conversationIds = page.getRecords().stream().map(ChatConversation::getId).toList();
        // 2. 获取未读消息数
        Map<Long, Integer> unreadCountMap = conversationUserMapper.selectList(new LambdaQueryWrapper<ChatConversationUser>()
                .select(ChatConversationUser::getConversationId, ChatConversationUser::getUnreadCount)
                .eq(ChatConversationUser::getUserId, userId)
                .in(ChatConversationUser::getConversationId, conversationIds)).stream().collect(Collectors.toMap(ChatConversationUser::getConversationId, ChatConversationUser::getUnreadCount));

        // 3. 获取各个对话的最后一条消息
        List<Long> lastMessageIds = page.getRecords().stream().map(ChatConversation::getLastMessageId).toList();
        List<ChatMessage> chatMessages = messageService.listByIds(lastMessageIds);
        Map<Long, ChatMessage> lastMessageMap = chatMessages.stream().collect(Collectors.toMap(ChatMessage::getConversationId, message -> message));
        // 4. 获取发送最后一条消息的用户昵称, TODO: 优化为批量发送
        Map<Long, String> nicknameMap = new HashMap<>();
        for (ChatMessage chatMessage : chatMessages) {
            nicknameMap.put(chatMessage.getConversationId(), userService.getUserById(chatMessage.getSenderId()).getNickname());
        }

        // 5. 获取参与对话的用户昵称
        Map<Long, List<Long>> participantsIds = conversationUserMapper.selectList(new LambdaQueryWrapper<ChatConversationUser>().select(ChatConversationUser::getUserId, ChatConversationUser::getConversationId).in(ChatConversationUser::getConversationId, conversationIds))
                .stream().collect(Collectors.groupingBy(ChatConversationUser::getConversationId, Collectors.mapping(ChatConversationUser::getUserId, Collectors.toList())));
        Map<Long, List<UserVO>> participantMap = participantsIds.keySet().stream().map(
                conversationId -> {
                    List<Long> userIds = participantsIds.get(conversationId);
                    List<UserVO> users = userService.getBatchByIds(userIds);
                    return Map.entry(conversationId, users);
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // 6. 构造ConversationVOs
        List<ConversationVO> conversationVos = page.getRecords().stream().map(chatConversation -> {
            ConversationVO conversationVO = BeanUtil.copyProperties(chatConversation, ConversationVO.class);
            conversationVO.setUnreadCount(unreadCountMap.getOrDefault(chatConversation.getId(), 0));
            conversationVO.setLastMessage(Optional.ofNullable(lastMessageMap.get(chatConversation.getId())).map(ChatMessage::getContent).orElse(null));
            conversationVO.setLastMessageType(Optional.ofNullable(lastMessageMap.get(chatConversation.getId())).map(ChatMessage::getType).orElse(null));
            conversationVO.setLastMessageNickname(nicknameMap.get(chatConversation.getId()));
            conversationVO.setParticipants(participantMap.get(chatConversation.getId()));
            return conversationVO;
        }).toList();

        return new PageResult<>(page.getTotal(), page.getPages(), conversationVos);
    }

    @Override
    @Transactional
    public ConversationVO createConversation(ConversationDTO conversationDTO) {
        // 1. 将当前用户ID存放到参与对话的用户id中
        List<Long> participantIds = conversationDTO.getParticipantIds();
        participantIds.add(SaTokenUtil.getId());

        // 2. 创建对话
        ChatConversation chatConversation = new ChatConversation();
        if (!this.save(chatConversation)) {
            throw new CommonException(ChatErrorCode.CREATE_CHAT_FAILED);
        }

        // 3. 建立用户对话关联
        List<ChatConversationUser> chatConversationUserList = participantIds.stream().map(participantId -> {
            return ChatConversationUser.builder()
                    .conversationId(chatConversation.getId())
                    .userId(participantId)
                    .build();
        }).toList();
        conversationUserMapper.insert(chatConversationUserList);

        // 4. 填充对话参与者信息并返回
        ConversationVO conversationVO = BeanUtil.copyProperties(chatConversation, ConversationVO.class);
        conversationVO.setParticipants(userService.getBatchByIds(participantIds));
        return conversationVO;
    }

    @Override
    @Transactional
    public Boolean deleteConversation(Long id) {
        // 1. 删除对话
        if (!this.removeById(id)) {
            throw new CommonException(ChatErrorCode.DELETE_CHAT_FAILED);
        }

        // 2. 删除用户对话关联
        // TODO: 优化为消息队列
        LambdaQueryWrapper<ChatConversationUser> wrapper = new LambdaQueryWrapper<ChatConversationUser>().eq(ChatConversationUser::getConversationId, id);
        if (conversationUserMapper.delete(wrapper) <= 0) {
            throw new CommonException(ChatErrorCode.DELETE_CHAT_FAILED);
        }
        return true;
    }
}
