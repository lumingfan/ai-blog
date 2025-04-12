package com.zeuslu.blog.common.domain;



import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.convert.Converter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author lumingfan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    protected Long total;
    protected Long pages;
    protected List<T> list;

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

    public static <T> PageResult<T> of(Page<?> page, List<T> list) {
        return new PageResult<>(page.getTotal(), page.getPages(), list);
    }

    public static <T, R> PageResult<T> of(Page<R> page, Class<T> clazz) {
        return new PageResult<>(page.getTotal(), page.getPages(), BeanUtil.copyToList(page.getRecords(), clazz));
    }
}
