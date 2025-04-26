package com.zeuslu.blog.common.handler;

import cn.dev33.satoken.exception.NotLoginException;
import com.zeuslu.blog.common.constant.HttpStatus;
import com.zeuslu.blog.common.domain.Result;
import com.zeuslu.blog.common.errorcode.BaseErrorCode;
import com.zeuslu.blog.common.exception.AbstractException;
import com.zeuslu.blog.common.exception.CommonException;
import com.zeuslu.blog.common.util.WebUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Objects;

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

    @ExceptionHandler(NotLoginException.class)
    public void handleNotLoginException(NotLoginException ex, HttpServletResponse response) {
        log.error("用户未登录 -> [{}:{}] [ex] {}", WebUtil.getMethod(), WebUtil.getCurrentUri(), ex.toString());
        response.setStatus(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public Result<?> handleRuntimeException(Exception ex) {
        log.error("其他异常 -> [{}:{}] [ex] {}", WebUtil.getMethod(), WebUtil.getCurrentUri(), ex.toString());
        return Result.fail(new CommonException(BaseErrorCode.SYSTEM_ERROR));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("参数校验异常 -> [{}:{}] [ex] {}", WebUtil.getMethod(), WebUtil.getCurrentUri(), ex.toString());
        return Result.fail(new CommonException(BaseErrorCode.PARAMS_ERROR.code(), Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public void handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletResponse response) {
        response.setStatus(HttpStatus.NOT_FOUND);
    }


}
