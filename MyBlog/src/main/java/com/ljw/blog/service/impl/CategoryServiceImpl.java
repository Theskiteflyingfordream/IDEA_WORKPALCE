package com.ljw.blog.service.impl;

import com.ljw.blog.entity.Category;
import com.ljw.blog.mapper.CategoryMapper;
import com.ljw.blog.service.ArticleService;
import com.ljw.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CategoryServiceImpl
 * @Description TODO
 * @DATE 2021/9/23 16:03
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Category getCategoryByIdWithArticle(Integer categoryId){

        return categoryMapper.selectCategoryWithArticle(categoryId);
    }

    @Override
    public List<Category> getCategoryList(){
        return categoryMapper.selectCategoryList();
    }

    @Override
    public int countCategory(){
        return categoryMapper.countCategory();
    }

}
