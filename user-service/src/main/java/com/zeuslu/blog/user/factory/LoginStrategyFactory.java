package com.zeuslu.blog.user.factory;

import com.zeuslu.blog.common.errorcode.UserErrorCode;
import com.zeuslu.blog.common.exception.CommonException;
import com.zeuslu.blog.user.strategy.LoginStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lumingfan
 */
@Component
@RequiredArgsConstructor
public class LoginStrategyFactory {
    private final List<LoginStrategy> loginStrategies;
    private Map<String, LoginStrategy> strategyMap;
    /**
     * 初始化登录策略map
     */
    private void initStrategyMap() {
        strategyMap = loginStrategies.stream()
                .collect(Collectors.toMap(LoginStrategy::getType, strategy -> strategy));
    }

    /**
     * 获取登录策略
     */
    public LoginStrategy getStrategy(String type) {
        initStrategyMap();
        LoginStrategy strategy = strategyMap.get(type);
        if (strategy == null) {
            throw new CommonException(UserErrorCode.UNSUPPORTED_LOGIN_TYPE);
        }
        return strategy;
    }


}
