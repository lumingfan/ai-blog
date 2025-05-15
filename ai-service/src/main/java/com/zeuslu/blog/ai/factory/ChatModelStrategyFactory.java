package com.zeuslu.blog.ai.factory;

import com.zeuslu.blog.ai.strategy.ChatModelStrategy;
import com.zeuslu.blog.common.errorcode.AiErrorCode;
import com.zeuslu.blog.common.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工厂类, 用于创建AI模型
 * @author lumingfan
 */
@Component
@RequiredArgsConstructor
public class ChatModelStrategyFactory {
    private final List<ChatModelStrategy> chatModelStrategyList;
    private Map<String, ChatModelStrategy> modelMap;

    /**
     * 初始化模型map
     */
    private void initModelMap() {
        modelMap = new HashMap<>();
        chatModelStrategyList.forEach(chatModelStrategy -> {
            chatModelStrategy.getTypes().forEach(type -> {
                modelMap.put(type, chatModelStrategy);
            });
        });
    }

    /**
     * 根据参数获取模型
     */
    public ChatModelStrategy getStrategy(String key) {
        // 懒加载
        initModelMap();
        ChatModelStrategy strategy = modelMap.get(key);
        if (strategy == null) {
            throw new CommonException(AiErrorCode.UNSUPPORTED_MODEL_TYPE);
        }
        return strategy;
    }
}
