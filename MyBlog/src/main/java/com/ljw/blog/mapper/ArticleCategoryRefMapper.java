package com.ljw.blog.mapper;

import com.ljw.blog.entity.ArticleCategoryRef;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ArticleCategoryRefMapper {
    int deleteByPrimaryKey(@Param("articleId") Integer articleId, @Param("categoryId") Integer categoryId);

    int insert(ArticleCategoryRef record);

    List<ArticleCategoryRef> selectAll();

    void deleteByArticleId(Integer articleId);
}