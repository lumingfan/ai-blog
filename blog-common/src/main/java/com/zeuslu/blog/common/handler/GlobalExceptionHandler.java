package com.zeuslu.blog.common.handler;

import com.zeuslu.blog.common.domain.Result;
import com.zeuslu.blog.common.errorcode.BaseErrorCode;
import com.zeuslu.blog.common.exception.AbstractException;
import com.zeuslu.blog.common.exception.CommonException;
import com.zeuslu.blog.common.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author lumingfan
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(AbstractException.class)
    public Result<?> handleAbstractException(AbstractException ex) {
        log.error("自定义异常 -> [{}:{}] [ex] {}", WebUtil.getMethod(), WebUtil.getCurrentUri(), ex.toString());
        return Result.fail(ex);
    }

    @ExceptionHandler(Exception.class)
    public Result<?> handleRuntimeException(Exception ex) {
        log.error("其他异常 -> [{}:{}] [ex] {}", WebUtil.getMethod(), WebUtil.getCurrentUri(), ex.toString());
        return Result.fail(new CommonException(BaseErrorCode.SYSTEM_ERROR));
    }
}
