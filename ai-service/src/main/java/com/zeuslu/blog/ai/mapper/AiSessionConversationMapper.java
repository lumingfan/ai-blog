package com.zeuslu.blog.ai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zeuslu.blog.api.ai.domain.po.AiSessionConversation;
import com.zeuslu.blog.api.ai.domain.vo.AiMessageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author lumingfan
 */
@Mapper
public interface AiSessionConversationMapper extends BaseMapper<AiSessionConversation> {
    /**
     * 根据会话id查询会话消息
     * @param sessionId 会话id
     */
    List<AiMessageVO> getSessionDetail(Long sessionId);

    void deleteBySessionId(Long sessionId);

    void deleteChatMemoryConversationBySessionId(Long sessionId);
}
