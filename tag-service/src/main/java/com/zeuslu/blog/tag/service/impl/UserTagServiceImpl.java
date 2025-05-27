package com.zeuslu.blog.tag.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeuslu.blog.api.tag.domain.po.UserTag;
import com.zeuslu.blog.tag.mapper.UserTagMapper;
import com.zeuslu.blog.tag.service.UserTagService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author lumingfan
*/
@Service
public class UserTagServiceImpl extends ServiceImpl<UserTagMapper, UserTag>
    implements UserTagService {
    @Override
    public List<String> getTagsByUserId(Long id) {
        // 1. 校验参数
        if (id == null) {
            return List.of();
        }
        // 2. 查询满足条件的标签列表
        return this.baseMapper.selectTagsByUserId(id);
    }
}




