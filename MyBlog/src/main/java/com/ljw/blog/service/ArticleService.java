package com.ljw.blog.service;

import com.github.pagehelper.PageInfo;
import com.ljw.blog.dto.ArticleParam;
import com.ljw.blog.entity.Article;
import com.ljw.blog.entity.Category;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ArticleService
 * @Description TODO
 * @DATE 2021/9/17 10:51
 */
public interface ArticleService {
    /*
     * @Description 分页查出文章
     * @Date 10:56 2021/9/17
     * @Param [pageIndex 从第几页开始, pageSize 一页的大小]
     * @ParamType [java.lang.Integer, java.lang.Integer]
     * @returnType com.github.pagehelper.PageInfo<com.ljw.blog.entity.Article>
     **/
    PageInfo<Article> getAllArticle(Integer pageIndex,Integer pageSize);

    PageInfo<Article> getArticlesByCriteriaWithPages(Integer pageIndex, Integer pageSize, Map<String,Object> criteria);

    Article getSingleArticleById(Integer articleId);

    Article getSingleArticleByIdWithCommentWithCategory(Integer articleId);

    Article getSinglePreArticlePublish(Integer articleId);

    Article getSingleAfterArticlePublish(Integer articleId);

    void updateArticleCommentCount(Integer articleId);

    void updateArticle(Article article);

    Integer countArticleByCriteria(Map<String,Object> criteria);

    Integer countViewByCriteria(Map<String,Object> criteria);

    Date getArticleLastUpdateTimeByCriteria(Map<String,Object> criteria);

    List<Article> getArticlesByCriteriaWithoutPages(Map<String,Object> criteria);

    void updateArticleDetail(ArticleParam articleParam, boolean updateTag, boolean updateCategory);

    void deleteArticleAndRef(Integer articleId);

}
