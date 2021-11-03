package com.ljw.blog.mapper;

import com.ljw.blog.entity.Category;
import java.util.List;
import java.util.Map;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer categoryId);

    int insert(Category record);

    Category selectByPrimaryKey(Integer categoryId);

    List<Category> selectAll();

    int updateByPrimaryKey(Category record);

    Category selectCategoryWithArticle(Integer categoryId);

    List<Category> selectCategoryList();

    int countCategory();

    List<Category> selectArticleCategoryList(Integer articleId);
}