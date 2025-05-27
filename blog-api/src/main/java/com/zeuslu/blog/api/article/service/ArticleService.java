package com.zeuslu.blog.api.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeuslu.blog.api.article.domain.dto.ArticlePageQuery;
import com.zeuslu.blog.api.article.domain.dto.SaveArticleDTO;
import com.zeuslu.blog.api.article.domain.po.Article;
import com.zeuslu.blog.api.article.domain.vo.ArticleDetailVO;
import com.zeuslu.blog.api.article.domain.vo.ArticleItemVO;
import com.zeuslu.blog.common.domain.PageResult;

/**
* @author lumingfan
* @description 针对表【tb_article(文章表)】的数据库操作Service
* @createDate 2025-04-27 21:50:04
*/
public interface ArticleService extends IService<Article> {

    PageResult<ArticleItemVO> pageArticle(ArticlePageQuery articlePageQuery);

    Long postArticle(SaveArticleDTO saveArticleDTO);

    ArticleDetailVO getArticleById(Long id);

    Long updateArticle(SaveArticleDTO saveArticleDTO);

    Boolean deleteArticleById(Long id);

    void incrementLikeCount(Long targetId);

    void decrementLikeCount(Long targetId);

    Integer getLikeCountByUserId(Long userId);

    Integer getArticleCountByUserId(Long id);
}
