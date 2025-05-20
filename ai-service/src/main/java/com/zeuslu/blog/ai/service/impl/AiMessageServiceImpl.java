package com.zeuslu.blog.ai.service.impl;

import com.zeuslu.blog.ai.factory.ChatModelStrategyFactory;
import com.zeuslu.blog.ai.mapper.AiSessionConversationMapper;
import com.zeuslu.blog.ai.service.AiMessageService;
import com.zeuslu.blog.ai.service.AiSessionService;
import com.zeuslu.blog.common.domain.PageQuery;
import com.zeuslu.blog.common.domain.PageResult;
import com.zeuslu.blog.common.enums.AiMessageRole;
import com.zeuslu.blog.common.errorcode.AiErrorCode;
import com.zeuslu.blog.common.exception.CommonException;
import com.zeuslu.blog.common.util.SaTokenUtil;
import com.zeuslu.blog.domain.dto.AiMessageDTO;
import com.zeuslu.blog.domain.po.AiSession;
import com.zeuslu.blog.domain.po.AiSessionConversation;
import com.zeuslu.blog.domain.vo.AiMessageVO;
import com.zeuslu.blog.domain.vo.AiSessionVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lumingfan
 */
@Service
@RequiredArgsConstructor
public class AiMessageServiceImpl implements AiMessageService {
    private final ChatModelStrategyFactory chatModelFactory;
    private final AiSessionService aiSessionService;
    private final AiSessionConversationMapper aiSessionConversationMapper;
    @Override
    public Flux<AiMessageVO> stream(AiMessageDTO aiMessageDTO) {
        Long userId = SaTokenUtil.getId();
        Flux<String> stream = chatModelFactory.getStrategy(aiMessageDTO.getModel().getModel()).stream(aiMessageDTO);
        Long sessionId = updateSession(aiMessageDTO, userId);
        return stream.map(content -> {
            AiMessageVO message = new AiMessageVO();
            message.setSessionId(sessionId);
            message.setContent(content);
            message.setRole(AiMessageRole.USER);
            message.setTimestamp(LocalDateTime.now());
            return message;
        });
    }

    @Override
    public String call(AiMessageDTO aiMessageDTO) {
        Long userId = SaTokenUtil.getId();
        String call = chatModelFactory.getStrategy(aiMessageDTO.getModel().getModel()).call(aiMessageDTO);
        updateSession(aiMessageDTO, userId);
        return call;
    }

    private Long updateSession(AiMessageDTO aiMessageDTO, Long userId) {
        // 创建会话
        Long sessionId = aiMessageDTO.getSessionId();
        if (sessionId == null) {
            String content = aiMessageDTO.getContent().getContent();
            AiSession session = AiSession.builder().userId(userId).summary(content.substring(0, Math.min(content.length(), 10))).build();
            aiSessionService.save(session);
            sessionId = session.getId();
        } else {
            // 校验会话id合法性
            if (!aiSessionService.lambdaQuery().eq(AiSession::getUserId, userId).eq(AiSession::getId, sessionId).exists()) {
                throw new CommonException(AiErrorCode.NOT_FOUND_SESSION);
            }
        }
        AiSessionConversation sessionConversation = AiSessionConversation.builder()
                .sessionId(sessionId)
                .conversationId(aiMessageDTO.getConversationId())
                .build();
        aiSessionConversationMapper.insert(sessionConversation);
        return sessionId;
    }

    @Override
    public PageResult<AiSessionVO> getSessions(PageQuery query) {
        return PageResult.of(
                aiSessionService.lambdaQuery()
                        .eq(AiSession::getUserId, SaTokenUtil.getId())
                        .orderBy(true, false, AiSession::getUpdatedAt)
                        .page(query.toPage()), AiSessionVO.class);
    }

    @Override
    public List<AiMessageVO> getSessionMessages(Long id) {
        return aiSessionConversationMapper.getSessionDetail(id);
    }

    @Override
    public Boolean deleteSession(Long id) {
        // 1. 删除session
        aiSessionService.removeById(id);

        // 2. TODO: 优化为消息队列后续异步删除
        aiSessionConversationMapper.deleteChatMemoryConversationBySessionId(id);
        aiSessionConversationMapper.deleteBySessionId(id);
        return true;
    }
}
