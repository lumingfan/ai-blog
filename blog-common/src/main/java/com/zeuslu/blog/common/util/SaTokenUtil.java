package com.zeuslu.blog.common.util;

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
        if (StpUtil.getLoginId() == null) {
            return null;
        }
        return Long.parseLong(StpUtil.getLoginId().toString());
    }
}
