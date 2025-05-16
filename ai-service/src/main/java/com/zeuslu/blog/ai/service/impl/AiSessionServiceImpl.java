package com.zeuslu.blog.ai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeuslu.blog.ai.mapper.AiSessionMapper;
import com.zeuslu.blog.ai.service.AiSessionService;
import com.zeuslu.blog.domain.po.AiSession;
import org.springframework.stereotype.Service;

/**
 * @author lumingfan
 */
@Service
public class AiSessionServiceImpl extends ServiceImpl<AiSessionMapper, AiSession> implements AiSessionService {
}
