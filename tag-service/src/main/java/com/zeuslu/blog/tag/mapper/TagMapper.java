package com.zeuslu.blog.tag.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zeuslu.blog.domain.po.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author lumingfan
* @description 针对表【tb_tag(标签表)】的数据库操作Mapper
* @createDate 2025-04-28 17:18:09
* @Entity generator.domain.Tag
*/
public interface TagMapper extends BaseMapper<Tag> {
    List<Long> selectArticles(List<String> tags, @Param(Constants.WRAPPER) LambdaQueryWrapper<Tag> wrapper);
    List<String> selectTagsByArticleId(Long articleId);
}




