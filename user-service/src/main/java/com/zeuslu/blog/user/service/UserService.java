package com.zeuslu.blog.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeuslu.blog.domain.dto.LoginDTO;
import com.zeuslu.blog.domain.dto.RegisterDTO;
import com.zeuslu.blog.domain.dto.UpdateUserDTO;
import com.zeuslu.blog.domain.po.User;
import com.zeuslu.blog.domain.vo.LoginResponseVO;
import com.zeuslu.blog.domain.vo.UserDetailVO;
import com.zeuslu.blog.domain.vo.UserVO;
import com.zeuslu.blog.domain.vo.UsernameCheckVO;

import java.util.List;

/**
* @author lumingfan
* @description 针对表【tb_user(用户管理表)】的数据库操作Service
* @createDate 2025-04-12 23:02:38
*/
public interface UserService extends IService<User> {

    void register(RegisterDTO registerDTO);

    LoginResponseVO login(LoginDTO loginDTO);

    void logout();

    UserVO getCurrentUser();

    UserVO getUserById(Long id);

    UserDetailVO getUserDetailById(Long id);

    UsernameCheckVO checkUsername(String username);

    Boolean validateToken();

    List<UserVO> getBatchByIds(List<Long> authorIds);

    UserDetailVO updateUser(UpdateUserDTO updateUserDTO);
}
