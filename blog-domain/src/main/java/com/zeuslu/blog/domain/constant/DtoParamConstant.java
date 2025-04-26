package com.zeuslu.blog.domain.constant;

/**
 * 数据传输对象参数校验相关常量
 *
 * @author lumingfan
 */
public class DtoParamConstant {
    /**
     * 密码格式
     */
    public static final String PASSWORD_REGEX_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$";

    /**
     * 账号格式
     */
    public static final int USERNAME_MIN_LENGTH = 3;
    public static final int USERNAME_MAX_LENGTH = 20;

    /**
     * 手机号格式
     */
    public static final String PHONE_REGEX_PATTERN = "^1[3-9]\\d{9}$";
}
