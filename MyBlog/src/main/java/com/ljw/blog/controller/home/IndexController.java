package com.ljw.blog.controller.home;

import com.github.pagehelper.PageInfo;
import com.ljw.blog.entity.Article;
import com.ljw.blog.enums.ArticleStatus;
import com.ljw.blog.service.ArticleService;
import com.ljw.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ArticleController
 * @Description TODO
 * @DATE 2021/9/17 11:04
 */

@Controller
public class IndexController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = {"/","/article"})
    public String Index(@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                        @RequestParam(required = false,defaultValue = "5") Integer pageSize,
                        Model model){
        HashMap<String,Object> criteria = new HashMap<>();
        criteria.put("status", ArticleStatus.Publish.getValue());
        PageInfo<Article> pageInfoArticle = articleService.getArticlesByCriteriaWithPages(pageIndex,pageSize,criteria);
        model.addAttribute("pageInfo",pageInfoArticle);
        model.addAttribute("urlPrefix","/article?pageIndex=");
        return "index";
    }

    //权限等错误
    @RequestMapping(value = "/403")
    public String to403JSP(){
        return "Error/403";
    }

    //系统错误
    @RequestMapping(value = "/400")
    public String to400JSP(){
        return "Error/400";
    }

}
