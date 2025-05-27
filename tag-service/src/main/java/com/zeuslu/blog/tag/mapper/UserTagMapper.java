package com.zeuslu.blog.tag.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zeuslu.blog.api.tag.domain.po.UserTag;

import java.util.List;

/**
 * @author lumingfan
 */
public interface UserTagMapper extends BaseMapper<UserTag> {
    List<String> selectTagsByUserId(Long userId);
}
