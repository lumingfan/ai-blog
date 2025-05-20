package com.zeuslu.blog.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeuslu.blog.domain.po.ChatConversation;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lumingfan
 */
@Mapper
public interface ConversationMapper extends BaseMapper<ChatConversation> {
    /**
     * 分页获取当前用户的对话记录
     * @param page
     * @param userId
     * @return
     */
    Page<ChatConversation> getConversationsByUserId(Page<ChatConversation> page, Long userId);
}
