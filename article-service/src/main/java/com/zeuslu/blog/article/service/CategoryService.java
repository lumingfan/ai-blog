package com.zeuslu.blog.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeuslu.blog.domain.po.Category;
import com.zeuslu.blog.domain.vo.CategoryVO;

import java.util.List;

/**
 * @author lumingfan
 */
public interface CategoryService extends IService<Category> {
    List<CategoryVO> getCategories();
}
