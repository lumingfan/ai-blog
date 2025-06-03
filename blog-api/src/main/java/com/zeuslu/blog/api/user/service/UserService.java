package com.zeuslu.blog.api.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeuslu.blog.api.user.domain.dto.LoginDTO;
import com.zeuslu.blog.api.user.domain.dto.RegisterDTO;
import com.zeuslu.blog.api.user.domain.dto.UpdateUserDTO;
import com.zeuslu.blog.api.user.domain.po.User;
import com.zeuslu.blog.api.user.domain.vo.LoginResponseVO;
import com.zeuslu.blog.api.user.domain.vo.UserProfileVO;
import com.zeuslu.blog.api.user.domain.vo.UserVO;
import com.zeuslu.blog.api.user.domain.vo.UsernameCheckVO;
import jakarta.validation.constraints.NotNull;

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

    UserProfileVO getUserDetailById(Long id);

    UsernameCheckVO checkUsername(String username);

    Boolean validateToken();

    List<UserVO> getBatchByIds(List<Long> authorIds);

    UserProfileVO updateUser(UpdateUserDTO updateUserDTO);

    Boolean followUser(@NotNull Long followingId);

    Boolean unFollowUser(@NotNull Long followingId);
}
