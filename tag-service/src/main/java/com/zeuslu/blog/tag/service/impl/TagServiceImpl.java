package com.zeuslu.blog.tag.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeuslu.blog.domain.po.Tag;
import com.zeuslu.blog.tag.mapper.TagMapper;
import com.zeuslu.blog.tag.service.TagService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
* @author lumingfan
* @description 针对表【tb_tag(标签表)】的数据库操作Service实现
* @createDate 2025-04-28 17:18:09
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService {

    @Override
    public List<Long> getArticleIdsByTagNames(List<String> tags) {
        // 1. 校验参数
        if (CollUtil.isEmpty(tags)) {
            return Collections.emptyList();
        }
        // 2. 查询满足条件的文章id列表
        return this.baseMapper.selectArticleIdsByTagNames(tags);
    }

    @Override
    public Long getTagIdByName(String tagName) {
        Tag tag = this.lambdaQuery().eq(Tag::getName, tagName).one();
        if (tag == null) {
            return null;
        }
        return tag.getId();
    }

    @Override
    public Long saveTag(String tagName) {
        Tag tag = new Tag();
        tag.setName(tagName);
        try {
            this.save(tag);
        } catch (DuplicateKeyException ex) {
            return this.getTagIdByName(tagName);
        }
        return tag.getId();
    }

    @Override
    public List<String> getTagsByArticleId(Long articleId) {
        return this.baseMapper.selectTagsByArticleId(articleId);
    }
}




