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
import com.zeuslu.blog.common.util.WebUtil;
import com.zeuslu.blog.domain.dto.ArticleDTO;
import com.zeuslu.blog.domain.dto.ArticleDetailVO;
import com.zeuslu.blog.domain.dto.ArticlePageQuery;
import com.zeuslu.blog.domain.po.Article;
import com.zeuslu.blog.domain.po.Category;
import com.zeuslu.blog.domain.vo.ArticleItemVO;
import com.zeuslu.blog.domain.vo.CategoryVO;
import com.zeuslu.blog.domain.vo.UserVO;
import com.zeuslu.blog.like.service.LikeService;
import com.zeuslu.blog.tag.service.ArticleTagService;
import com.zeuslu.blog.tag.service.TagService;
import com.zeuslu.blog.user.service.UserService;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    private final LikeService likeService;

    public ArticleServiceImpl(UserService userService, TagService tagService, ArticleTagService articleTagService, CategoryService categoryService, LikeService likeService) {
        this.userService = userService;
        this.tagService = tagService;
        this.articleTagService = articleTagService;
        this.categoryService = categoryService;
        this.likeService = likeService;
    }

    @Override
    public PageResult<ArticleItemVO> pageArticle(ArticlePageQuery articlePageQuery) {
        // 1. 获取分页参数和必要的过滤参数
        Page<Article> page = articlePageQuery.toPage();
        Long authorId = articlePageQuery.getAuthorId();
        Long categoryId = articlePageQuery.getCategoryId();
        List<String> tags = articlePageQuery.getTags();

        // 2. 调用标签服务根据标签过滤文章id
        List<Long> articleIds = tagService.getArticleIdsByTagNames(tags);

        if (CollUtil.isNotEmpty(tags) && CollUtil.isEmpty(articleIds)) {
            // tags 不为空, articleIds为空, 说明该标签下无文章
            return PageResult.of(page, article -> null);
        }

        // 3. 根据条件分页查询数据
        page = this.lambdaQuery()
                .eq(authorId != null, Article::getAuthorId, authorId)
                .eq(categoryId != null, Article::getCategoryId, categoryId)
                .eq(Article::getDraft, false)
                // 当传入标签时, 才进行过滤, 否则articleIds为空,导致查不出数据
                .in(CollUtil.isNotEmpty(tags), Article::getId, articleIds)
                .page(page);

        // 4. 通过用户服务批量获取作者信息
        List<Long> authorIds = page.getRecords().stream().map(Article::getAuthorId).toList();
        Map<Long, UserVO> authors = userService.getBatchByIds(authorIds).stream().collect(Collectors.toMap(UserVO::getId, Function.identity()));

        // 5. 返回转换为PageResult<ArticleItemVO>的结果
        return PageResult.of(page, article -> {
            // 6. 相同属性直接转换
            ArticleItemVO articleItemVO = BeanUtil.copyProperties(article, ArticleItemVO.class);
            // 7. 发布时间
            articleItemVO.setPublishedAt(article.getCreatedAt());
            // 8. 作者信息
            articleItemVO.setAuthor(authors.get(article.getAuthorId()));
            // 9. 标签信息
            articleItemVO.setTags(tagService.getTagsByArticleId(article.getId()));
            // 10. 分类信息
            articleItemVO.setCategory(BeanUtil.copyProperties(categoryService.getById(article.getCategoryId()), CategoryVO.class));
            // 11. 点赞信息
            articleItemVO.setIsLiked(
                    SaTokenUtil.getId() != null && likeService.isUserLikeArticle(SaTokenUtil.getId(), article.getId())
            );

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
                tagService::saveTag
        ).toList();

        // 3. 插入标签-文章数据库
        if (CollUtil.isNotEmpty(tagIds) && !articleTagService.saveArticleTags(article.getId(), tagIds)) {
            throw new CommonException(ArticleErrorCode.PUBLISH_FAILED);
        }

        // 4. 增加该分类下文章数量
        categoryService.lambdaUpdate()
                .eq(Category::getId, article.getCategoryId())
                .setIncrBy(Category::getCount, 1)
                .update();
        return article.getId();
    }

    @Override
    public ArticleDetailVO getArticleById(Long id) throws NoResourceFoundException {
        // 1. 查询文章
        Article article = this.getById(id);
        if (article == null) {
            throw new NoResourceFoundException(HttpMethod.GET, Objects.requireNonNull(WebUtil.getCurrentUri()));
        }

        // 2. 查询作者
        UserVO author = userService.getUserById(article.getAuthorId());

        // 3. 查询分类
        CategoryVO category = BeanUtil.copyProperties(categoryService.getById(article.getCategoryId()), CategoryVO.class);

        // 4. 查询标签
        List<String> tags = tagService.getTagsByArticleId(id);

        ArticleDetailVO articleDetailVO = BeanUtil.copyProperties(article, ArticleDetailVO.class);
        articleDetailVO.setAuthor(author);
        articleDetailVO.setCategory(category);
        articleDetailVO.setTags(tags);

        // 5. 查询当前用户是否点赞/收藏该文章
        articleDetailVO.setIsLiked(
                SaTokenUtil.getId() != null && likeService.isUserLikeArticle(SaTokenUtil.getId(), id)
        );

        // TODO 更新阅读量
        return articleDetailVO;
    }

    @Override
    public Long updateArticle(ArticleDTO articleDTO) {
        // 1. 校验是否是当前作者
        Long authorId = this.lambdaQuery().select(Article::getAuthorId).eq(Article::getId, articleDTO.getId()).one().getAuthorId();
        if (authorId == null || !authorId.equals(SaTokenUtil.getId())) {
            throw new CommonException(ArticleErrorCode.NOT_AUTHOR);
        }

        boolean updated = this.lambdaUpdate()
                .eq(Article::getId, articleDTO.getId())
                .set(articleDTO.getTitle() != null, Article::getTitle, articleDTO.getTitle())
                .set(articleDTO.getContent() != null, Article::getContent, articleDTO.getContent())
                .set(articleDTO.getSummary() != null, Article::getSummary, articleDTO.getSummary())
                .set(articleDTO.getDraft() != null, Article::getDraft, articleDTO.getDraft())
                .set(articleDTO.getCoverImage() != null, Article::getCoverImage, articleDTO.getCoverImage())
                .set(articleDTO.getCategoryId() != null, Article::getCategoryId, articleDTO.getCategoryId())
                .update();
        if (!updated) {
            throw new CommonException(ArticleErrorCode.PUBLISH_FAILED);
        }
        // 更新标签
        List<String> originTags = tagService.getTagsByArticleId(articleDTO.getId());
        if (!CollUtil.isEqualList(originTags, articleDTO.getTags())) {
            // 1. 删除旧的标签映射
            if (!tagService.removeTagArticleMap(articleDTO.getId())) {
                throw new CommonException(ArticleErrorCode.PUBLISH_FAILED);
            }
            // 2. 增加新标签
            List<Long> tagIds = articleDTO.getTags().stream().map(
                    tagService::saveTag
            ).toList();
            // 3. 插入标签-文章数据库
            if (CollUtil.isNotEmpty(tagIds) && !articleTagService.saveArticleTags(articleDTO.getId(), tagIds)) {
                throw new CommonException(ArticleErrorCode.PUBLISH_FAILED);
            }
        }
        return articleDTO.getId();
    }

    @Override
    public Boolean deleteArticleById(Long id) {
        // 1. 检查是否为文章作者
        Long authorId = this.lambdaQuery().select(Article::getAuthorId).eq(Article::getId, id).one().getAuthorId();
        if (authorId == null || !authorId.equals(SaTokenUtil.getId())) {
            throw new CommonException(ArticleErrorCode.NOT_AUTHOR);
        }
        return this.removeById(id);
    }

    @Override
    public void incrementLikeCount(Long targetId) {
        this.lambdaUpdate()
                .eq(Article::getId, targetId)
                .setIncrBy(Article::getLikeCount, 1)
                .update();
    }

    @Override
    public void decrementLikeCount(Long targetId) {
        this.lambdaUpdate()
                .eq(Article::getId, targetId)
                .setDecrBy(Article::getLikeCount, 1)
                .update();
    }
}




