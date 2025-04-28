package com.zeuslu.blog.tag.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeuslu.blog.domain.po.ArticleTag;

import java.util.List;

/**
* @author lumingfan
*/
public interface ArticleTagService extends IService<ArticleTag> {
    boolean saveArticleTags(Long id, List<Long> tagIds);
}
