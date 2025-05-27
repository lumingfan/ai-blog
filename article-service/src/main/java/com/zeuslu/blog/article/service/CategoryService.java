package com.zeuslu.blog.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeuslu.blog.api.article.domain.po.Category;
import com.zeuslu.blog.api.article.domain.vo.ArticleCategoryVO;

import java.util.List;

/**
 * @author lumingfan
 */
public interface CategoryService extends IService<Category> {
    List<ArticleCategoryVO> getCategories();
}
