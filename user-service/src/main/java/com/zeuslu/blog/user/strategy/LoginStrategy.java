package com.zeuslu.blog.user.strategy;

import com.zeuslu.blog.domain.dto.LoginDTO;
import com.zeuslu.blog.domain.po.User;

/**
 * 登录策略类
 * @author lumingfan
 */
public interface LoginStrategy {
    /**
     * 执行登录策略, 并返回登录用户, 失败直接抛出异常
     */
    User login(LoginDTO loginDTO);

    /**
     * 获取策略类型
     */
    String getType();
}
