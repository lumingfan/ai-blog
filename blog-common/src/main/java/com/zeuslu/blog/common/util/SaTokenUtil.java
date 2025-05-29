package com.zeuslu.blog.common.util;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.SaTokenContextException;
import cn.dev33.satoken.stp.StpUtil;

/**
 * SaToken 工具类
 * @author lumingfan
 */
public class SaTokenUtil {
    /**
     * 从token中获取用户id
     */
    public static Long getId() {
        try {
            if (StpUtil.getLoginId() == null) {
                return null;
            }
            return Long.parseLong(StpUtil.getLoginId().toString());
        } catch (NotLoginException | SaTokenContextException e) {
            return null;
        }
    }
}
