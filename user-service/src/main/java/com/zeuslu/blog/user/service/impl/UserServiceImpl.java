package com.zeuslu.blog.user.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeuslu.blog.domain.dto.UserLoginDTO;
import com.zeuslu.blog.domain.dto.UserRegisterDTO;
import com.zeuslu.blog.domain.po.User;
import com.zeuslu.blog.user.mapper.UserMapper;
import com.zeuslu.blog.user.service.UserService;
import org.springframework.stereotype.Service;

/**
* @author lumingfan
* @description 针对表【tb_user(用户管理表)】的数据库操作Service实现
* @createDate 2025-04-12 23:02:38
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Override
    public void register(UserRegisterDTO userRegisterDTO) {

    }

    @Override
    public SaTokenInfo login(UserLoginDTO loginDTO) {
        return null;
    }
}




