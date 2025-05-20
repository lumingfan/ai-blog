package com.zeuslu.blog.user.controller;

import cn.hutool.core.bean.BeanUtil;
import com.zeuslu.blog.common.annotation.Log;
import com.zeuslu.blog.common.domain.Result;
import com.zeuslu.blog.common.errorcode.UserErrorCode;
import com.zeuslu.blog.common.exception.CommonException;
import com.zeuslu.blog.domain.dto.UpdateUserDTO;
import com.zeuslu.blog.domain.vo.UserDetailVO;
import com.zeuslu.blog.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public Result<UserDetailVO> getUserDetailById(@PathVariable Long id) {
        return Result.ok(userService.getUserDetailById(id));
    }

    @PutMapping
    @Operation(summary = "更新用户信息")
    public Result<UserDetailVO> update(@RequestBody UpdateUserDTO updateUserDTO) {
        if (BeanUtil.isEmpty(updateUserDTO)) {
            throw new CommonException(UserErrorCode.USER_UPDATE_PARAM_ERROR);
        }
        return Result.ok(userService.updateUser(updateUserDTO));
    }
}
