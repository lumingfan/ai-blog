package com.zeuslu.blog.article.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeuslu.blog.article.mapper.ArticleMapper;
import com.zeuslu.blog.article.service.ArticleService;
import com.zeuslu.blog.article.service.CategoryService;
import com.zeuslu.blog.common.domain.PageResult;
import com.zeuslu.blog.common.errorcode.ArticleErrorCode;
import com.zeuslu.blog.common.exception.CommonException;
import com.zeuslu.blog.common.util.SaTokenUtil;
import com.zeuslu.blog.domain.dto.ArticleDTO;
import com.zeuslu.blog.domain.dto.ArticlePageQuery;
import com.zeuslu.blog.domain.po.Article;
import com.zeuslu.blog.domain.po.Category;
import com.zeuslu.blog.domain.vo.ArticleItemVO;
import com.zeuslu.blog.domain.vo.UserVO;
import com.zeuslu.blog.tag.service.ArticleTagService;
import com.zeuslu.blog.tag.service.TagService;
import com.zeuslu.blog.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
* @author lumingfan
* @description 针对表【tb_article(文章表)】的数据库操作Service实现
* @createDate 2025-04-27 21:50:04
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService {

    private final UserService userService;
    private final TagService tagService;
    private final ArticleTagService articleTagService;
    private final CategoryService categoryService;

    public ArticleServiceImpl(UserService userService, TagService tagService, ArticleTagService articleTagService, CategoryService categoryService) {
        this.userService = userService;
        this.tagService = tagService;
        this.articleTagService = articleTagService;
        this.categoryService = categoryService;
    }

    @Override
    public PageResult<ArticleItemVO> getArticleList(ArticlePageQuery articlePageQuery) {
        // 1. 获取分页参数和必要的过滤参数
        Page<Article> page = articlePageQuery.toPage();
        Long authorId = articlePageQuery.getAuthorId();
        Long categoryId = articlePageQuery.getCategoryId();
        List<String> tags = articlePageQuery.getTags();

        // 2. 调用标签服务根据标签过滤文章id
        List<Long> articleIds = tagService.getArticleIdsByTags(tags);

        // 3. 根据条件分页查询数据
        page = this.lambdaQuery()
                .eq(authorId != null, Article::getAuthorId, authorId)
                .eq(categoryId != null, Article::getCategoryId, categoryId)
                // 当传入标签时, 才进行过滤, 否则articleIds为空,导致查不出数据
                .in(CollUtil.isNotEmpty(tags), Article::getId, articleIds)
                .page(page);

        // 4. 通过用户服务批量获取作者信息
        List<Long> authorIds = page.getRecords().stream().map(Article::getAuthorId).toList();
        Map<Long, UserVO> authors = userService.getBatchByIds(authorIds).stream().collect(Collectors.toMap(UserVO::getId, Function.identity()));

        // 5. TODO: 通过点赞服务获取当前用户是否点赞了该文章

        // 6. 返回转换为PageResult<ArticleItemVO>的结果
        return PageResult.of(page, article -> {
            // 7. 相同属性直接转换
            ArticleItemVO articleItemVO = BeanUtil.copyProperties(article, ArticleItemVO.class);
            // 8. 发布时间
            articleItemVO.setPublishedAt(article.getCreatedAt());
            // 9. 作者信息
            articleItemVO.setAuthor(authors.get(article.getAuthorId()));
            // 10. 标签信息
            articleItemVO.setTags(tagService.getTagsByArticleId(article.getId()));
            // 11. TODO: 点赞信息

            return articleItemVO;
        });
    }

    @Override
    @Transactional
    public Long postArticle(ArticleDTO articleDTO) {
        // 1. 保存文章到文章数据库表
        Article article = BeanUtil.copyProperties(articleDTO, Article.class);
        article.setAuthorId(SaTokenUtil.getId());
        if (!this.save(article)) {
            throw new CommonException(ArticleErrorCode.PUBLISH_FAILED);
        }

        // 2. 根据标签数据库表查询标签id,没有查询到的标签直接保存到标签数据库中,并返回标签id
        List<Long> tagIds = articleDTO.getTags().stream().map(
                tag -> {
                    Long tagId = tagService.getTagIdByName(tag);
                    if (tagId == null) {
                        tagId = tagService.saveTag(tag);
                    }
                    return tagId;
                }
        ).toList();

        // 3. 插入标签-文章数据库
        if (!articleTagService.saveArticleTags(article.getId(), tagIds)) {
            throw new CommonException(ArticleErrorCode.PUBLISH_FAILED);
        }

        // 4. 增加该分类下文章数量
        categoryService.lambdaUpdate()
                .eq(Category::getId, article.getCategoryId())
                .setIncrBy(Category::getCount, 1)
                .update();
        return article.getId();
    }
}




