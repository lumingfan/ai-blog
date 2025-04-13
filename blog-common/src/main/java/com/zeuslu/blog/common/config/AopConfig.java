package com.zeuslu.blog.common.config;

import com.zeuslu.blog.common.aop.LogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注入AOP切面
 *
 * @author lumingfan
 */
@Configuration
public class AopConfig {
    @Bean
    public LogAspect logAspect() {
        return new LogAspect();
    }
}
