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
    public static final String PHONE_REGEX_PATTERN = "^(?:(?:\\+|00)86)?1(?:(3[\\d])|(4[5-79])|(5[0-35-9])|(6[5-7])|(7[0-8])|(8[\\d])|(9[189]))\\d{8}$";


    /**
     * 邮箱格式
     */
    public static final String EMAIL_REGEX_PATTERN = "^([a-zA-Z]|[0-9])(\\w|\\-)+@[a-zA-Z0-9]+\\.([a-zA-Z]{2,4})$";
}
