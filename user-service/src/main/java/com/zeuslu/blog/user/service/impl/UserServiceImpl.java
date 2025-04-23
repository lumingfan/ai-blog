package com.zeuslu.blog.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeuslu.blog.common.errorcode.UserErrorCode;
import com.zeuslu.blog.common.exception.CommonException;
import com.zeuslu.blog.domain.dto.UserLoginDTO;
import com.zeuslu.blog.domain.dto.UserRegisterDTO;
import com.zeuslu.blog.domain.dto.UserUpdateDTO;
import com.zeuslu.blog.domain.po.User;
import com.zeuslu.blog.domain.vo.TokenVO;
import com.zeuslu.blog.domain.vo.UserVO;
import com.zeuslu.blog.storage.factory.StorageFactory;
import com.zeuslu.blog.storage.util.StorageStrategy;
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

    private final String AVATAR_DIR_PREFIX = "avatar";


    @Override
    public TokenVO register(UserRegisterDTO userRegisterDTO) {
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
        User user = User.builder()
                .username(username)
                .password(encrypt)
                .nickname(DEFAULT_NICKNAME_PREFIX + RandomUtil.randomString(DEFAULT_NICKNAME_SUFFIX_LEN))
                .build();
        try {
            boolean saved = this.save(user);
            if (!saved) {
                throw new CommonException(UserErrorCode.REGISTER_FAILED);
            }
            // 获取字段默认值
            user = this.getById(user.getId());
        } catch (DuplicateKeyException e) {
            throw new CommonException(UserErrorCode.USER_EXISTED);
        }
        StpUtil.login(user.getId());
        return TokenVO.builder()
                .token(StpUtil.getTokenInfo().tokenValue)
                .userInfo(BeanUtil.copyProperties(user, UserVO.class)).build();
    }

    @Override
    public TokenVO login(UserLoginDTO loginDTO) {
        // 1. 参数接收和验证格式(validation已经完成)
        // 2. 用户存在性校验
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        User user = this.lambdaQuery().eq(User::getUsername, username).one();
        if (user == null) {
            throw new CommonException(UserErrorCode.BAD_USER_PASSWORD);
        }
        // 3. 密码验证
        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new CommonException(UserErrorCode.BAD_USER_PASSWORD);
        }
        // 4. 生成登录凭证并返回
        StpUtil.login(user.getId());
        return TokenVO.builder()
                .token(StpUtil.getTokenInfo().tokenValue)
                .userInfo(BeanUtil.copyProperties(user, UserVO.class)).build();
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
    public UserVO updateUserInfo(UserUpdateDTO userUpdateDTO) {
        // 1. 上传用户头像
        String avatar = null;
        if (userUpdateDTO.getAvatar() != null) {
            StorageStrategy storageService = storageFactory.getStorageService();
            avatar = storageService.uploadFile(userUpdateDTO.getAvatar(), AVATAR_DIR_PREFIX);

        }
        Long id = Long.parseLong(StpUtil.getLoginId().toString());
        this.lambdaUpdate().eq(User::getId, id)
                .set(avatar != null, User::getAvatar, avatar)
                .set(userUpdateDTO.getNickname() != null, User::getNickname, userUpdateDTO.getNickname())
                .set(userUpdateDTO.getEmail() != null, User::getEmail, userUpdateDTO.getEmail())
                .set(userUpdateDTO.getPhone() != null, User::getPhone, userUpdateDTO.getPhone())
                .update();
        return BeanUtil.copyProperties(this.getById(id), UserVO.class);
    }
}




