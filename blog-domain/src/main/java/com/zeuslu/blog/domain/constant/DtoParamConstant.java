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
    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final int PASSWORD_MAX_LENGTH = 20;

    /**
     * 手机号格式
     */
    public static final String PHONE_REGEX_PATTERN = "^1[3|5|7|8|9]\\d{9}$";


    /**
     * 邮箱格式
     */
    public static final String EMAIL_REGEX_PATTERN = "^([a-zA-Z]|[0-9])(\\w|\\-)+@[a-zA-Z0-9]+\\.([a-zA-Z]{2,4})$";
}
