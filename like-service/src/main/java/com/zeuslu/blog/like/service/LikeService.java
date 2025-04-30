package com.zeuslu.blog.like.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeuslu.blog.domain.po.Like;
import com.zeuslu.blog.domain.vo.LikeResponseVO;

/**
 * @author lumingfan
 */
public interface LikeService extends IService<Like> {
    LikeResponseVO likeArticle(Long id);

    LikeResponseVO unLikeArticle(Long id);

    Boolean isUserLikeArticle(Long userId, Long articleId);
}
