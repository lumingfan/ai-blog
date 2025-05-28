package com.zeuslu.blog.api.like.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeuslu.blog.api.like.domain.po.Like;
import com.zeuslu.blog.api.like.domain.vo.LikeResponseVO;

/**
 * @author lumingfan
 */
public interface LikeService extends IService<Like> {
    LikeResponseVO likeArticle(Long id);

    LikeResponseVO unLikeArticle(Long id);

    Boolean isUserLikeArticle(Long userId, Long articleId);

    Integer getUserLikesCount(Long userId);

    Integer getCommentLikeCount(Long commentId);

    Boolean isUserLikeComment(Long userId, Long commentId);

    Integer getCommentReplyLikeCount(Long commentReplyId);

    Boolean isUserLikeReply(Long userId, Long replyId);
}
