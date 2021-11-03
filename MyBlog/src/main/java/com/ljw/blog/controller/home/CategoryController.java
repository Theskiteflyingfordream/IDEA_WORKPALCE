package com.ljw.blog.controller.home;

import com.ljw.blog.entity.Category;
import com.ljw.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @ClassName CategoryController
 * @Description TODO
 * @DATE 2021/9/22 15:52
 */

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/category/{categoryId}",method = RequestMethod.GET)
    public String viewCategory(@PathVariable("categoryId") Integer categoryId, Model model){
        /*查出该categoryId下的所有文章*/
        Category category = categoryService.getCategoryByIdWithArticle(categoryId);
        /*将其添加到model中*/
        model.addAttribute("category",category);
        /*返回到category.jsp中*/
        return "category";
    }


}
