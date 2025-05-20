package com.zeuslu.blog.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zeuslu.blog.domain.po.ChatConversationUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lumingfan
 */
@Mapper
public interface ConversationUserMapper extends BaseMapper<ChatConversationUser> {

}
