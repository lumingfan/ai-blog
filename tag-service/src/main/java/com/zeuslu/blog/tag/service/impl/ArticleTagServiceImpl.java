package com.zeuslu.blog.tag.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeuslu.blog.domain.po.ArticleTag;
import com.zeuslu.blog.tag.mapper.ArticleTagMapper;
import com.zeuslu.blog.tag.service.ArticleTagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author lumingfan
*/
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag>
    implements ArticleTagService {
    @Override
    @Transactional
    public boolean saveArticleTags(Long articleId, List<Long> tagIds) {
        return this.saveBatch(tagIds.stream().map(tagId -> {
            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticleId(articleId);
            articleTag.setTagId(tagId);
            return articleTag;
        }).toList());
    }
}




