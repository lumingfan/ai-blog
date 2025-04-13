package com.zeuslu.blog.common.aop;

import com.zeuslu.blog.common.constant.ConsoleColors;
import com.zeuslu.blog.common.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 日志AOP切面, 对于所有标记了@Log注解的controller方法都进行日志调用记录,
 * 要求返回结果必须是Result
 * @author lumingfan
 */
@Slf4j
@Aspect
public class LogAspect {
    private static final String SPLIT_LINE = "--------------------------------------------------";

    /**
     *  切点表达式：匹配所有带有 @Log 注解的类中的方法
     */
    @Around("@within(com.zeuslu.blog.common.annotation.Log) && execution(* com.zeuslu.blog.*..controller.*Controller.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取请求信息
        String method = WebUtil.getMethod();
        String uri = WebUtil.getCurrentUri();

        log.info(ConsoleColors.ANSI_BLUE + SPLIT_LINE + ConsoleColors.ANSI_RESET);
        log.info(ConsoleColors.ANSI_BLUE + "请求路径 -> [{}:{}], 请求方法 -> {}, 参数 -> {}" + ConsoleColors.ANSI_RESET,
                method, uri, joinPoint.getSignature().getName(), joinPoint.getArgs());
        Object result = joinPoint.proceed();
        log.info(ConsoleColors.ANSI_BLUE + "返回结果: {}" + ConsoleColors.ANSI_RESET, result);
        log.info(ConsoleColors.ANSI_BLUE + SPLIT_LINE + ConsoleColors.ANSI_RESET);
        return result;
    }
}
