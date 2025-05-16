package com.zeuslu.blog.ai.service;

import com.zeuslu.blog.common.domain.PageQuery;
import com.zeuslu.blog.common.domain.PageResult;
import com.zeuslu.blog.domain.dto.AiMessageDTO;
import com.zeuslu.blog.domain.vo.AiMessageVO;
import com.zeuslu.blog.domain.vo.AiSessionVO;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author lumingfan
 */
public interface AiMessageService {
    Flux<AiMessageVO> stream(AiMessageDTO aiMessageDTO);
    String call(AiMessageDTO aiMessageDTO);

    PageResult<AiSessionVO> getSessions(PageQuery query);

    List<AiMessageVO> getSessionMessages(Long id);

    Boolean deleteSession(Long id);
}
