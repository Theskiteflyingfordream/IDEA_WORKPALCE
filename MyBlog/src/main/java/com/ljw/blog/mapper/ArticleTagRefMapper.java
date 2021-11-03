package com.ljw.blog.mapper;

import com.ljw.blog.entity.ArticleTagRef;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ArticleTagRefMapper {
    int deleteByPrimaryKey(@Param("articleId") Integer articleId, @Param("tagId") Integer tagId);

    int insert(ArticleTagRef record);

    List<ArticleTagRef> selectAll();

    void deleteByArticleId(Integer articleId);
}