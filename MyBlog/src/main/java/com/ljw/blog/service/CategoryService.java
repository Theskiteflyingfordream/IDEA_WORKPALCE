package com.ljw.blog.service;

import com.ljw.blog.entity.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    Category getCategoryByIdWithArticle(Integer categoryId);

    List<Category> getCategoryList ();

    int countCategory();

}
