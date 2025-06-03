package com.zeuslu.blog.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeuslu.blog.api.user.domain.po.UserFollow;
import com.zeuslu.blog.user.mapper.UserFollowMapper;
import com.zeuslu.blog.user.service.UserFollowService;
import org.springframework.stereotype.Service;

/**
 * @author lumingfan
 */
@Service
public class UserFollowServiceImpl extends ServiceImpl<UserFollowMapper, UserFollow> implements UserFollowService {
}
