package com.zeuslu.blog.user.controller;

import com.zeuslu.blog.common.annotation.Log;
import com.zeuslu.blog.common.domain.Result;
import com.zeuslu.blog.domain.vo.UserDetailVO;
import com.zeuslu.blog.domain.vo.UserVO;
import com.zeuslu.blog.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理接口
 * @author lumingfan
 */
@Tag(name = "用户管理接口")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
@Log
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "根据用户id获取用户详细信息")
    public Result<UserDetailVO> getUserById(@PathVariable Long id) {
        return Result.ok(userService.getUserById(id));
    }
}
