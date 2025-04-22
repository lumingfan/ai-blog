package com.zeuslu.blog.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeuslu.blog.domain.dto.UserLoginDTO;
import com.zeuslu.blog.domain.dto.UserRegisterDTO;
import com.zeuslu.blog.domain.po.User;
import com.zeuslu.blog.domain.vo.TokenVO;
import com.zeuslu.blog.domain.vo.UserVO;

/**
* @author lumingfan
* @description 针对表【tb_user(用户管理表)】的数据库操作Service
* @createDate 2025-04-12 23:02:38
*/
public interface UserService extends IService<User> {

    TokenVO register(UserRegisterDTO userRegisterDTO);

    TokenVO login(UserLoginDTO loginDTO);

    void logout();

    UserVO getCurrentUser();
}
