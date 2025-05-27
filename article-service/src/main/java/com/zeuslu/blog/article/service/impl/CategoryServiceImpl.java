package com.zeuslu.blog.article.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeuslu.blog.article.mapper.CategoryMapper;
import com.zeuslu.blog.article.service.CategoryService;
import com.zeuslu.blog.api.article.domain.po.Category;
import com.zeuslu.blog.api.article.domain.vo.ArticleCategoryVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lumingfan
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService{
    @Override
    public List<ArticleCategoryVO> getCategories() {
        return BeanUtil.copyToList(this.list(), ArticleCategoryVO.class);
    }
}
