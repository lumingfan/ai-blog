package com.zeuslu.blog.chat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeuslu.blog.chat.mapper.ConversationUserMapper;
import com.zeuslu.blog.chat.service.ConversationUserService;
import com.zeuslu.blog.domain.po.ChatConversationUser;
import org.springframework.stereotype.Service;

@Service
public class ConversationUserServiceImpl extends ServiceImpl<ConversationUserMapper, ChatConversationUser> implements ConversationUserService {
}
