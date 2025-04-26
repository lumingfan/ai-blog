package com.zeuslu.blog.user.controller;

import com.zeuslu.blog.common.annotation.Log;
import com.zeuslu.blog.common.domain.Result;
import com.zeuslu.blog.domain.constant.DtoParamConstant;
import com.zeuslu.blog.domain.dto.LoginDTO;
import com.zeuslu.blog.domain.dto.RegisterDTO;
import com.zeuslu.blog.domain.vo.LoginResponseVO;
import com.zeuslu.blog.domain.vo.UsernameCheckVO;
import com.zeuslu.blog.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author lumingfan
 */
@Tag(name = "认证接口")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
@Log
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "用户注册接口")
    public Result<?> register(@Valid @RequestBody RegisterDTO registerDTO) {
        userService.register(registerDTO);
        return Result.ok();
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录接口")
    public Result<LoginResponseVO> login(@Valid @RequestBody LoginDTO loginDTO) {
        return Result.ok(userService.login(loginDTO));
    }

    @GetMapping("/check-username")
    @Operation(summary = "检查用户名是否存在")
    public Result<UsernameCheckVO> checkUsername(
            @RequestParam
            @Valid
            @Size(min=DtoParamConstant.USERNAME_MIN_LENGTH, max=DtoParamConstant.USERNAME_MAX_LENGTH)
            String username) {
        return Result.ok(userService.checkUsername(username));
    }

    @PostMapping("/logout")
    @Operation(summary = "用户登出接口")
    public Result<?> logout() {
        userService.logout();
        return Result.ok();
    }

    @GetMapping("/validate-token")
    @Operation(summary = "检查用户登录状态接口")
    public Result<Boolean> validateToken() {
        return Result.ok(userService.validateToken());
    }
}
