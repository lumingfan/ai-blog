package com.zeuslu.blog.user.strategy.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.zeuslu.blog.common.errorcode.UserErrorCode;
import com.zeuslu.blog.common.exception.CommonException;
import com.zeuslu.blog.api.user.domain.dto.LoginDTO;
import com.zeuslu.blog.api.user.domain.po.User;
import com.zeuslu.blog.user.constant.UserConstant;
import com.zeuslu.blog.user.strategy.LoginStrategy;


/**
 * 用户名登录策略实现类
 * @author lumingfan
 */
public class UsernameLoginStrategy implements LoginStrategy {
    @Override
    public User login(LoginDTO loginDTO) {
        // 1. 用户存在性校验
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        User user = Db.lambdaQuery(User.class).eq(User::getUsername, username).one();
        if (user == null) {
            throw new CommonException(UserErrorCode.USERNAME_OR_PASSWORD_ERROR);
        }
        // 2. 密码验证
        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new CommonException(UserErrorCode.USERNAME_OR_PASSWORD_ERROR);
        }
        return user;
    }

    @Override
    public String getType() {
        return UserConstant.LOGIN_TYPE_USERNAME;
    }
}
