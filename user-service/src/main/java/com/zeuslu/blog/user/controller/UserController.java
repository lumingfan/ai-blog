package com.zeuslu.blog.user.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.zeuslu.blog.common.domain.Result;
import com.zeuslu.blog.domain.dto.UserLoginDTO;
import com.zeuslu.blog.domain.dto.UserRegisterDTO;
import com.zeuslu.blog.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理接口
 * @author lumingfan
 */
@Tag(name = "用户管理接口")
@RestController("/user")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "用户注册接口")
    public Result<?> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        userService.register(userRegisterDTO);
        return Result.ok();
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录接口")
    public Result<SaTokenInfo> login(@Valid @RequestBody UserLoginDTO loginDTO) {
        return Result.ok(userService.login(loginDTO));
    }


}
