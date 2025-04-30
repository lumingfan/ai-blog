package com.zeuslu.blog.article.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeuslu.blog.article.mapper.CategoryMapper;
import com.zeuslu.blog.article.service.CategoryService;
import com.zeuslu.blog.domain.po.Category;
import com.zeuslu.blog.domain.vo.CategoryVO;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author lumingfan
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService{
    @Override
    public List<CategoryVO> getCategories() {
        return BeanUtil.copyToList(this.list(), CategoryVO.class);
    }
}
