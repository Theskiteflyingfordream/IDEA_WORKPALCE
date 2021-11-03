package com.ljw.blog.mapper;

import com.github.pagehelper.PageInfo;
import com.ljw.blog.entity.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface ArticleMapper {
    int deleteByPrimaryKey(Integer articleId);

    int insert(Article record);

    Article selectByPrimaryKey(Integer articleId);

    List<Article> selectAll();

    int updateByPrimaryKey(Article record);

    List<Article> selectArticlesByCriteria(Map<String,Object> criteria);

    Article selectByPrimaryKeyWithCommentWithCategory(Integer articleId);

    Article selectPreArticlePublish(Integer articleId);

    Article selectAfterArticlePublish(Integer articleId);

    void updateArticleCommentCount(Integer articleId);

    int countArticle(Map<String,Object> criteria);

    Integer countViewByCriteria(Map<String,Object> criteria);

    Date getArticleLastUpdateTimeByCriteria(Map<String,Object> criteria);

    void deleteByPrimaryKeyAndRef(Integer articleId);
}