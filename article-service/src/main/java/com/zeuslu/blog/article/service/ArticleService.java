package com.zeuslu.blog.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeuslu.blog.common.domain.PageResult;
import com.zeuslu.blog.domain.dto.ArticleDTO;
import com.zeuslu.blog.domain.dto.ArticlePageQuery;
import com.zeuslu.blog.domain.po.Article;
import com.zeuslu.blog.domain.vo.ArticleItemVO;

/**
* @author lumingfan
* @description 针对表【tb_article(文章表)】的数据库操作Service
* @createDate 2025-04-27 21:50:04
*/
public interface ArticleService extends IService<Article> {

    PageResult<ArticleItemVO> pageArticle(ArticlePageQuery articlePageQuery);

    Long postArticle(ArticleDTO articleDTO);
}
