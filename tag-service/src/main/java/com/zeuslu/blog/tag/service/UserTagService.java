package com.zeuslu.blog.tag.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeuslu.blog.api.tag.domain.po.UserTag;

import java.util.List;

/**
 * @author lumingfan
 */
public interface UserTagService extends IService<UserTag> {
    List<String> getTagsByUserId(Long id);
}
