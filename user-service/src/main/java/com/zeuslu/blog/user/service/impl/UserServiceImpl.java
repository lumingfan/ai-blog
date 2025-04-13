package com.zeuslu.blog.user.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeuslu.blog.common.errorcode.UserErrorCode;
import com.zeuslu.blog.common.exception.CommonException;
import com.zeuslu.blog.domain.dto.UserLoginDTO;
import com.zeuslu.blog.domain.dto.UserRegisterDTO;
import com.zeuslu.blog.domain.po.User;
import com.zeuslu.blog.user.mapper.UserMapper;
import com.zeuslu.blog.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * @author lumingfan
 * @description 针对表【tb_user(用户管理表)】的数据库操作Service实现
 * @createDate 2025-04-12 23:02:38
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    public static final String DEFAULT_NICKNAME_PREFIX = "user:";
    public static final Integer DEFAULT_NICKNAME_SUFFIX_LEN = 10;

    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        // 1. 参数接收和验证(格式: controller + validation已经完成; 密码一致性)
        String username = userRegisterDTO.getUsername();
        String password = userRegisterDTO.getPassword();
        String repeatedPassword = userRegisterDTO.getRepeatedPassword();

        if (!StrUtil.equals(password, repeatedPassword)) {
            throw new CommonException(UserErrorCode.BAD_REPEATED_PASSWORD);
        }

        // 2.用户名唯一性校验
        if (this.lambdaQuery().eq(User::getUsername, username).exists()) {
            throw new CommonException(UserErrorCode.USER_EXISTED);
        }

        // 3.密码加密, hutool的BCrypt算法生成hashpw为60字符, 因此至少保证数据库密码字段60个字符
        String encrypt = BCrypt.hashpw(password, BCrypt.gensalt());

        // 4.创建用户账户
        try {
            this.save(User.builder()
                    .username(username)
                    .password(encrypt)
                    .nickname(DEFAULT_NICKNAME_PREFIX + RandomUtil.randomString(DEFAULT_NICKNAME_SUFFIX_LEN))
                    .build());
        } catch (DuplicateKeyException e) {
            throw new CommonException(UserErrorCode.USER_EXISTED);
        }
    }

    @Override
    public SaTokenInfo login(UserLoginDTO loginDTO) {
        return null;
    }
}




