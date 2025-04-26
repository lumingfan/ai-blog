package com.zeuslu.blog.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeuslu.blog.common.errorcode.UserErrorCode;
import com.zeuslu.blog.common.exception.CommonException;
import com.zeuslu.blog.domain.dto.LoginDTO;
import com.zeuslu.blog.domain.dto.RegisterDTO;
import com.zeuslu.blog.domain.po.User;
import com.zeuslu.blog.domain.vo.LoginResponseVO;
import com.zeuslu.blog.domain.vo.UserVO;
import com.zeuslu.blog.domain.vo.UsernameCheckVO;
import com.zeuslu.blog.storage.factory.StorageFactory;
import com.zeuslu.blog.user.factory.LoginStrategyFactory;
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
    private final StorageFactory storageFactory;
    private final LoginStrategyFactory loginStrategyFactory;

    private final String AVATAR_DIR_PREFIX = "avatar";
    private final String AVATAR_FIELD = "avatar";
    private final String DEFAULT_AVATAR_URL = "https://aiblog-1305314451.cos.ap-shanghai.myqcloud.com/avatar%2Fdefault_avatar.svg";

    @Override
    public void register(RegisterDTO registerDTO) {
        // 1. 参数接收和验证(格式: controller + validation已经完成; 密码一致性)
        String username = registerDTO.getUsername();
        String password = registerDTO.getPassword();
        String repeatedPassword = registerDTO.getRepeatedPassword();

        if (!StrUtil.equals(password, repeatedPassword)) {
            throw new CommonException(UserErrorCode.REPEATED_PASSWORD_NON_CONSISTENT);
        }

        // 2.用户名唯一性校验
        if (this.lambdaQuery().eq(User::getUsername, username).exists()) {
            throw new CommonException(UserErrorCode.USERNAME_EXISTED);
        }

        // 3.密码加密, hutool的BCrypt算法生成hashpw为60字符, 因此至少保证数据库密码字段60个字符
        String encrypt = BCrypt.hashpw(password, BCrypt.gensalt());

        // 4.创建用户账户
        User user = User.builder()
                .username(username)
                .password(encrypt)
                .nickname(DEFAULT_NICKNAME_PREFIX + RandomUtil.randomString(DEFAULT_NICKNAME_SUFFIX_LEN))
                .build();
        try {
            boolean saved = this.save(user);
            if (!saved) {
                throw new CommonException(UserErrorCode.INTERNAL_SERVER_ERROR);
            }
            // 获取字段默认值
            user = this.getById(user.getId());
        } catch (DuplicateKeyException e) {
            throw new CommonException(UserErrorCode.USERNAME_EXISTED);
        }
        StpUtil.login(user.getId());
    }

    @Override
    public LoginResponseVO login(LoginDTO loginDTO) {
        // 1. 参数接收和验证格式(validation已经完成)
        // 2. 根据请求类型调用不同的登录方法
        User user = loginStrategyFactory.getStrategy(loginDTO.getType()).login(loginDTO);
        // 3. 生成登录凭证并返回
        StpUtil.login(user.getId());
        return LoginResponseVO.builder()
                .token(StpUtil.getTokenInfo().tokenValue)
                .user(BeanUtil.copyProperties(user, UserVO.class)).build();
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }

    @Override
    public UserVO getCurrentUser() {
        // 1. 获取当前登录用户的id
        Long userId = Long.parseLong(StpUtil.getLoginId().toString());
        // 2. 查询用户信息并返回脱敏后的信息
        User user = this.getById(userId);
        return BeanUtil.copyProperties(user, UserVO.class);
    }

    @Override
    public UserVO getUserById(Long id) {
        return BeanUtil.copyProperties(this.getById(id), UserVO.class);
    }

    @Override
    public UsernameCheckVO checkUsername(String username) {
        // 1.查询用户名是否存在
        boolean existed = this.lambdaQuery().eq(User::getUsername, username).exists();
        // 2.返回结果
        return UsernameCheckVO.builder()
                .available(!existed)
                .message(UserErrorCode.USERNAME_EXISTED.message())
                .build();
    }

    @Override
    public Boolean validateToken() {
        return StpUtil.isLogin();
    }
}




