package com.ljw.blog.controller.home;

import com.ljw.blog.entity.Article;
import com.ljw.blog.entity.User;
import com.ljw.blog.enums.ArticleStatus;
import com.ljw.blog.enums.UserRole;
import com.ljw.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ArticleController
 * @Description TODO
 * @DATE 2021/9/18 13:28
 */

@Controller
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @RequestMapping(value = "/article/{articleId}",method = RequestMethod.GET)
    public String viewArticle(@PathVariable("articleId") Integer articleId, Model model, HttpSession httpSession){
        /*根据Id获取文章*/
        Article article = articleService.getSingleArticleByIdWithCommentWithCategory(articleId);
        model.addAttribute("article",article);
        /*获取猜你喜欢的文章*/
        Map<String,Object> criteria = new HashMap<> ();
        criteria.put("similar",true);
        criteria.put("status", ArticleStatus.Publish.getValue());
        criteria.put("articleId",article.getArticleId());
        criteria.put("limit",5);
        List<Article> similarArticleList = articleService.getArticlesByCriteriaWithoutPages(criteria);
        model.addAttribute("similarArticleList",similarArticleList);
        /*获取热评文章*/
        criteria.put("similar",null);
        criteria.put("limit",8);
        criteria.put("whetherComment",true);
        List<Article> mostCommentArticleList = articleService.getArticlesByCriteriaWithoutPages(criteria);
        model.addAttribute("mostCommentArticleList",mostCommentArticleList);
        /*获取该文章的上一篇文章*/
        model.addAttribute("preArticle",articleService.getSinglePreArticlePublish(articleId));
        /*获取该文章的下一篇文章*/
        model.addAttribute("afterArticle",articleService.getSingleAfterArticlePublish(articleId));

        return "article";
    }

    @RequestMapping(value = "/article/view/{articleId}")
    @ResponseBody
    public String increaseArticleView(@PathVariable("articleId") Integer articleId){
        Article article = articleService.getSingleArticleById(articleId);
        Integer viewCount = article.getArticleViewCount();
        viewCount = viewCount + 1;
        article.setArticleViewCount(viewCount);
        articleService.updateArticle(article);
        return viewCount.toString();
    }

    @RequestMapping(value = "/article/like/{articleId}")
    @ResponseBody
    public String increaseArticleLike(@PathVariable("articleId") Integer articleId){
        Article article = articleService.getSingleArticleById(articleId);
        Integer likeCount = article.getArticleLikeCount();
        likeCount = likeCount + 1;
        article.setArticleLikeCount(likeCount);
        articleService.updateArticle(article);
        return likeCount.toString();
    }

    @RequestMapping(value = "/search")
    public String articlSearch(@RequestParam("keywords") String keywords,Model model){
        Map<String,Object> criteria = new HashMap<>();
        criteria.put("status",ArticleStatus.Publish.getValue());
        criteria.put("keywords",keywords);
        List<Article> articleList = articleService.getArticlesByCriteriaWithoutPages(criteria);
        model.addAttribute("searchArticleList",articleList);
        return "search";
    }


}
