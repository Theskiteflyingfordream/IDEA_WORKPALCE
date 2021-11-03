package com.ljw.blog.interceptor;

import com.ljw.blog.entity.Category;
import com.ljw.blog.entity.Comment;
import com.ljw.blog.enums.ArticleStatus;
import com.ljw.blog.service.ArticleService;
import com.ljw.blog.service.CategoryService;
import com.ljw.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName HomeResourceInterceptor
 * @Description TODO
 * @DATE 2021/9/16 16:47
 */

@Component
public class HomeResourceInterceptor implements HandlerInterceptor {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ArticleService articleService;

    @Autowired
    CommentService commentService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception{
        /*获取分类*/
        List<Category> categoryList = categoryService.getCategoryList();
        request.setAttribute("categoryList",categoryList);
        /*获取网站概况：文章总数，留言总数，分类总数，浏览总量，最后更新，最新的十条评论（同时显示对应的文章）*/
        Map<String,Object> basicStatistics = new HashMap<>();
        Map<String,Object> criteria = new HashMap<String,Object>();
        criteria.put("status",ArticleStatus.Publish.getValue());
        basicStatistics.put("articleCount",articleService.countArticleByCriteria(criteria));
        basicStatistics.put("commentCount",commentService.countComment());
        basicStatistics.put("categoryCount",categoryService.countCategory());
        basicStatistics.put("viewCount",articleService.countViewByCriteria(criteria));
        basicStatistics.put("lastUpdateTime",articleService.getArticleLastUpdateTimeByCriteria(criteria));
        basicStatistics.put("latestTenComment",commentService.getLatestTenComment());
        request.setAttribute("basicStatistics",basicStatistics);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}
