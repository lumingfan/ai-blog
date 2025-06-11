package com.zeuslu.blog.common.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import com.zeuslu.blog.common.enums.ArticleSortByEnums;
import com.zeuslu.blog.common.enums.CommentType;
import com.zeuslu.blog.common.enums.NotificationType;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lumingfan
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        // 需要排除swagger相关路径, 否则访问不到api文档
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/auth/login",
                        "/auth/register",
                        "/auth/check-username/**",
                        "/categories/**",
                        "/articles/page",
                        "/articles/detail/**"
                )
                .excludePathPatterns(
                        "/error",
                        "/favicon.ico",
                        "/v2/**",
                        "/v3/**",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/doc.html"
                );
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToArticleSortByEnumsConverter());
        registry.addConverter(new StringToNotificationTypeConverter());
        registry.addConverter(new StringToCommentTypeConverter());
    }

    /**
     * 字符串转换为 ArticleSortByEnums 枚举类的转换器
     */
    private static class StringToArticleSortByEnumsConverter implements Converter<String, ArticleSortByEnums> {
        @Override
        public ArticleSortByEnums convert(String source) {
            return ArticleSortByEnums.fromValue(source);
        }
    }

    /**
     * 字符串转换为NotificationType枚举类的转换器
     */
    private static class StringToNotificationTypeConverter implements Converter<String, NotificationType> {
        @Override
        public NotificationType convert(String source) {
            return NotificationType.fromValue(source);
        }
    }

    /**
     * 字符串转换为CommentType枚举类的转换器
     */
    private static class StringToCommentTypeConverter implements Converter<String, CommentType> {
        @Override
        public CommentType convert(String source) {
            return CommentType.fromValue(source);
        }
    }
}
