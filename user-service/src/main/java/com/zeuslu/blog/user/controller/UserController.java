package com.zeuslu.blog.user.controller;

import cn.hutool.core.bean.BeanUtil;
import com.zeuslu.blog.common.annotation.Log;
import com.zeuslu.blog.common.domain.Result;
import com.zeuslu.blog.domain.dto.UserLoginDTO;
import com.zeuslu.blog.domain.dto.UserRegisterDTO;
import com.zeuslu.blog.domain.dto.UserUpdateDTO;
import com.zeuslu.blog.domain.vo.TokenVO;
import com.zeuslu.blog.domain.vo.UserVO;
import com.zeuslu.blog.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户管理接口
 * @author lumingfan
 */
@Tag(name = "用户管理接口")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
@Log
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "用户注册接口")
    public Result<TokenVO> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        return Result.ok(userService.register(userRegisterDTO));
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录接口")
    public Result<TokenVO> login(@Valid @RequestBody UserLoginDTO loginDTO) {
        return Result.ok(userService.login(loginDTO));
    }

    @PostMapping("/logout")
    @Operation(summary = "用户登出接口")
    public Result<Void> logout() {
        userService.logout();
        return Result.ok();
    }

    @GetMapping
    @Operation(summary = "获取当前用户信息接口")
    public Result<UserVO> getCurrentUser() {
        return Result.ok(userService.getCurrentUser());
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据用户id获取用户信息")
    public Result<UserVO> getUserById(@PathVariable Long id) {
        return Result.ok(userService.getUserById(id));
    }


    @PutMapping
    @Operation(summary = "更新用户信息")
    public Result<UserVO> updateUserInfo(
            @RequestPart(value = "avatar", required = false) MultipartFile avatar,
            @RequestPart(value = "userInfo", required = false) @Valid UserUpdateDTO userUpdateDTO) {
        // 如果没有传递userUpdateDTO和avatar则直接返回
        if (avatar == null && (userUpdateDTO == null || BeanUtil.isEmpty(userUpdateDTO))) {
            return Result.ok(userService.getCurrentUser());
        }
        // 设置头像
        userUpdateDTO.setAvatar(avatar);
        return Result.ok(userService.updateUserInfo(userUpdateDTO));
    }
}
