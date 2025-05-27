package com.zeuslu.blog.tag.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zeuslu.blog.api.tag.domain.po.Tag;

import java.util.List;

/**
* @author lumingfan
* @description 针对表【tb_tag(标签表)】的数据库操作Mapper
* @createDate 2025-04-28 17:18:09
* @Entity generator.domain.Tag
*/
public interface TagMapper extends BaseMapper<Tag> {
    List<Long> selectArticleIdsByTagNames(List<String> tags);
    List<String> selectTagsByArticleId(Long articleId);
}




