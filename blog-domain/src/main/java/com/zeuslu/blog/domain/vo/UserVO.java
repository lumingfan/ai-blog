package com.zeuslu.blog.domain.vo;

import com.zeuslu.blog.domain.enums.UserRoleEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息视图对象
 * @author lumingfan
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户信息视图对象")
public class UserVO {
    /**
     * 主键ID
     */
    @Schema(description = "用户ID", example = "1001")
    private Long id;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称", example = "张三")
    private String nickname;

    /**
     * 用户账号名
     */
    @Schema(description = "用户账号名", example = "zhangsan")
    private String username;

    /**
     * 用户手机号
     */
    @Schema(description = "用户手机号", example = "13800138000")
    private String phone;

    /**
     * 用户邮箱
     */
    @Schema(description = "用户邮箱", example = "zhangsan@example.com")
    private String email;

    /**
     * 用户权限
     */
    @Schema(description = "用户权限", example = "NORMAL_USER")
    private UserRoleEnums role;

    /**
     * 用户头像
     */
    @Schema(description = "用户头像")
    private String avatar;
}