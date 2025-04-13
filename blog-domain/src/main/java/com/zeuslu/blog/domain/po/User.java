package com.zeuslu.blog.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户管理表
 * @author lumingfan
 * @TableName tb_user
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="tb_user")
@Data
public class User extends Base {
    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户账号名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户邮箱
     */
    private String email;
}