package com.zeuslu.blog.user.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zeuslu.blog.domain.dto.UserLoginDTO;
import com.zeuslu.blog.domain.dto.UserRegisterDTO;
import com.zeuslu.blog.domain.po.User;

/**
* @author lumingfan
* @description 针对表【tb_user(用户管理表)】的数据库操作Service
* @createDate 2025-04-12 23:02:38
*/
public interface UserService extends IService<User> {

    void register(UserRegisterDTO userRegisterDTO);

    SaTokenInfo login(UserLoginDTO loginDTO);
}
