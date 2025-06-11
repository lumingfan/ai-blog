package com.zeuslu.blog.ai.factory;

import com.zeuslu.blog.ai.strategy.ChatModelStrategy;
import com.zeuslu.blog.common.enums.AiModelEnums;
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
    private Map<AiModelEnums, ChatModelStrategy> modelMap;

    /**
     * 初始化模型map
     */
    private void initModelMap() {
        modelMap = new HashMap<>();
        chatModelStrategyList.forEach(chatModelStrategy -> {
            chatModelStrategy.getTypes().forEach(enumType -> {
                modelMap.put(enumType, chatModelStrategy);
            });
        });
    }

    /**
     * 根据参数获取模型
     */
    public ChatModelStrategy getStrategy(AiModelEnums enumType) {
        // 懒加载
        initModelMap();
        ChatModelStrategy strategy = modelMap.get(enumType);
        if (strategy == null) {
            throw new CommonException(AiErrorCode.UNSUPPORTED_MODEL_TYPE);
        }
        return strategy;
    }
}
