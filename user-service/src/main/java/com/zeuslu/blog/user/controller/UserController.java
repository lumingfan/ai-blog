package com.zeuslu.blog.user.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.zeuslu.blog.common.annotation.Log;
import com.zeuslu.blog.common.domain.Result;
import com.zeuslu.blog.domain.dto.UserLoginDTO;
import com.zeuslu.blog.domain.dto.UserRegisterDTO;
import com.zeuslu.blog.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public Result<SaTokenInfo> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        return Result.ok(userService.register(userRegisterDTO));
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录接口")
    public Result<SaTokenInfo> login(@Valid @RequestBody UserLoginDTO loginDTO) {
        return Result.ok(userService.login(loginDTO));
    }

    @GetMapping("/logout")
    @Operation(summary = "用户登出接口")
    public Result<Void> logout() {
        userService.logout();
        return Result.ok();
    }

}
