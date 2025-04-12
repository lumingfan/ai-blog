package com.zeuslu.blog.common.context;

/**
 * @author lumingfan
 * 封装ThreadLocal并提供一些基本操作
 */
public class TokenContext {
    private TokenContext(){}
    private static final ThreadLocal<String> TOKEN_THREAD_LOCAL = new ThreadLocal<>();

    public static void set(String value) {
        TOKEN_THREAD_LOCAL.set(value);
    }

    public static String get() {
        return TOKEN_THREAD_LOCAL.get();
    }

    public static void remove() {
        TOKEN_THREAD_LOCAL.remove();
    }
}
