package com.zeuslu.blog.user.config;

import com.zeuslu.blog.user.strategy.LoginStrategy;
import com.zeuslu.blog.user.strategy.impl.UsernameLoginStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置登录策略
 * @author lumingfan
 */
@Configuration
public class LoginStrategyConfig {
    @Bean
    public List<LoginStrategy> loginStrategyList() {
        List<LoginStrategy> loginStrategies = new ArrayList<>();
        loginStrategies.add(new UsernameLoginStrategy());
        return loginStrategies;
    }
}
