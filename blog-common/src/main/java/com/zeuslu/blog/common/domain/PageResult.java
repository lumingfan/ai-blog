package com.zeuslu.blog.common.domain;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * @author lumingfan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    protected Long total;
    protected Long pages;
    protected List<T> contents;

    public static <T> PageResult<T> empty(Long total, Long pages) {
        return new PageResult<>(total, pages, Collections.emptyList());
    }
    public static <T> PageResult<T> empty(Page<?> page) {
        return new PageResult<>(page.getTotal(), page.getPages(), Collections.emptyList());
    }

    public static <T> PageResult<T> of(Page<T> page) {
        if(page == null){
            return new PageResult<>();
        }
        if (CollUtil.isEmpty(page.getRecords())) {
            return empty(page);
        }
        return new PageResult<>(page.getTotal(), page.getPages(), page.getRecords());
    }

    /**
     * 给定结果的list, 将Page<T>转换为PageResult<T>
     */
    public static <T> PageResult<T> of(Page<?> page, List<T> list) {
        return new PageResult<>(page.getTotal(), page.getPages(), list);
    }
    /**
     * 给定结果类型, 将Page<R>转换为PageResult<T>
     */
    public static <T, R> PageResult<T> of(Page<R> page, Class<T> clazz) {
        return new PageResult<>(page.getTotal(), page.getPages(), BeanUtil.copyToList(page.getRecords(), clazz));
    }


    /**
     * 自定义转换函数, 将Page<R>转换为PageResult<T>
     */
    public static <T, R> PageResult<T> of(Page<R> page, Function<R, T> function) {
        return new PageResult<>(page.getTotal(), page.getPages(), page.getRecords().stream().map(function).toList());
    }
}
