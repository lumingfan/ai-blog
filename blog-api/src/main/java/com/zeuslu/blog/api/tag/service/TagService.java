package com.zeuslu.blog.api.tag.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeuslu.blog.api.tag.domain.po.Tag;

import java.util.List;

/**
* @author lumingfan
* @description 针对表【tb_tag(标签表)】的数据库操作Service
* @createDate 2025-04-28 17:18:09
*/
public interface TagService extends IService<Tag> {

    List<Long> getArticleIdsByTagNames(List<String> tags);

    Long getTagIdByName(String tagName);

    Long saveTag(String tagName);

    List<String> getTagsByArticleId(Long articleId);

    Boolean removeTagArticleMap(Long articleId);

    List<String> getTagsByUserId(Long id);

    boolean saveArticleTags(Long id, List<Long> tagIds);
}
