package com.ljw.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ljw.blog.dto.ArticleParam;
import com.ljw.blog.entity.*;
import com.ljw.blog.mapper.ArticleCategoryRefMapper;
import com.ljw.blog.mapper.ArticleMapper;
import com.ljw.blog.mapper.ArticleTagRefMapper;
import com.ljw.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ArticleServiceImpl
 * @Description TODO
 * @DATE 2021/9/17 10:57
 */

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleCategoryRefMapper articleCategoryRefMapper;

    @Autowired
    private ArticleTagRefMapper articleTagRefMapper;

    /*
     * @Description 查出所有文章并分页,无状态判断
     * @Date 10:20 2021/9/18
     * @Param [pageIndex, pageSize]
     * @ParamType [java.lang.Integer, java.lang.Integer]
     * @returnType com.github.pagehelper.PageInfo<com.ljw.blog.entity.Article>
     **/
    @Override
    public PageInfo<Article> getAllArticle(Integer pageIndex, Integer pageSize){
        PageHelper.startPage(pageIndex,pageSize);
        List<Article> articleList = articleMapper.selectAll();
        return new PageInfo<>(articleList);
    }

    @Override
    public PageInfo<Article> getArticlesByCriteriaWithPages(Integer pageIndex, Integer pageSize, Map<String,Object> criteria){
        PageHelper.startPage(pageIndex,pageSize);
        List<Article> articleList = articleMapper.selectArticlesByCriteria(criteria);
        return new PageInfo<>(articleList);
    }

    @Override
    public Article getSingleArticleById(Integer articleId){
        return articleMapper.selectByPrimaryKey(articleId);
    }

    @Override
    public Article getSingleArticleByIdWithCommentWithCategory(Integer articleId){
        return articleMapper.selectByPrimaryKeyWithCommentWithCategory(articleId);
    }

    @Override
    public Article getSinglePreArticlePublish(Integer articleId){
        return articleMapper.selectPreArticlePublish(articleId);
    }

    @Override
    public Article getSingleAfterArticlePublish(Integer articleId){
        return articleMapper.selectAfterArticlePublish(articleId);
    }

    @Override
    public void updateArticleCommentCount(Integer articleId){
        articleMapper.updateArticleCommentCount(articleId);
    }

    @Override
    public void updateArticle(Article article){
        articleMapper.updateByPrimaryKey(article);
    }

    @Override
    public Integer countArticleByCriteria(Map<String,Object> criteria){
        return articleMapper.countArticle(criteria);
    }

    @Override
    public Integer countViewByCriteria(Map<String,Object> criteria){
        return articleMapper.countViewByCriteria(criteria);
    }

    @Override
    public Date getArticleLastUpdateTimeByCriteria(Map<String,Object> criteria){
        return articleMapper.getArticleLastUpdateTimeByCriteria(criteria);
    }

    @Override
    public List<Article> getArticlesByCriteriaWithoutPages(Map<String,Object> criteria){
        return articleMapper.selectArticlesByCriteria(criteria);
    }

    //Note:事务管理器如果没有回退，注意看数据库的表使用的存储引擎是否支持事务
    @Override
    public void updateArticleDetail(ArticleParam articleParam, boolean updateTag, boolean updateCategory) {
        Article articleToInsert = new Article();
        articleToInsert.setArticleId(articleParam.getArticleId());
        articleToInsert.setArticleTitle(articleParam.getArticleTitle());
        articleToInsert.setArticleContent(articleParam.getArticleContent());
        articleToInsert.setArticleStatus(articleParam.getArticleStatus());
        articleMapper.updateByPrimaryKey(articleToInsert);
        Integer articleId = articleToInsert.getArticleId();
        //更新标签
        if(updateTag){
            articleTagRefMapper.deleteByArticleId(articleId);
            List<Integer> tagIds = articleParam.getArticleTagIds();
            if(tagIds!=null){
                for (int i = 0; i < tagIds.size(); i++) {
                    articleTagRefMapper.insert(new ArticleTagRef(articleId,tagIds.get(i)));
                }
            }
        }
        //更新分类
        if(updateCategory){
            articleCategoryRefMapper.deleteByArticleId(articleId);
            if(articleParam.getCategoryParent()!=null) articleCategoryRefMapper.insert(new ArticleCategoryRef(articleId, articleParam.getCategoryParent()));
            if(articleParam.getCategoryChild()!=null) articleCategoryRefMapper.insert(new ArticleCategoryRef(articleId, articleParam.getCategoryChild()));
        }
    }

    //删除文章同时删除相关的分类ref和标签ref
    @Override
    public void deleteArticleAndRef(Integer articleId){
        articleMapper.deleteByPrimaryKeyAndRef(articleId);
    }

}
