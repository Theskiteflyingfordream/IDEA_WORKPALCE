package com.ljw.blog.controller.home;

import cn.hutool.http.HtmlUtil;
import com.ljw.blog.dto.JsonResult;
import com.ljw.blog.entity.Article;
import com.ljw.blog.entity.Comment;
import com.ljw.blog.entity.User;
import com.ljw.blog.enums.Role;
import com.ljw.blog.service.ArticleService;
import com.ljw.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Objects;

/**
 * @ClassName CommentController
 * @Description TODO
 * @DATE 2021/9/19 9:54
 */

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = {"/comment"},method = RequestMethod.POST)
    @ResponseBody
    public JsonResult submitComment(Comment comment, HttpSession session){
        /*待做:后端校验当前用户是否登录*/
        /*注意拿出来需要强制类型转换*/
        User user = (User) session.getAttribute("user");
        if(user==null){
            return new JsonResult().fail().add("failMsg","请先登录");
        }
        /*后端校验取出来的文章是否存在,注意前端中的隐藏域可以f12修改,因此必须校验*/
        Article article = articleService.getSingleArticleById(comment.getCommentArticleId());
        if(article == null){
            return new JsonResult().fail().add("failMsg","插入失败,不存在该篇文章");
        }
        /*待做：补充传入的评论的剩余信息*/
        comment.setCommentUserId(user.getUserId());
        comment.setCommentCreateTime(new Date());
        if (Objects.equals(user.getUserId(), article.getArticleUserId())) {
            comment.setCommentRole(Role.OWNER.getValue());
        } else {
            comment.setCommentRole(Role.VISITOR.getValue());
        }
        //comment.setCommentAuthorAvatar(user.getUserAvatar());
        //过滤字符，防止XSS攻击
        comment.setCommentContent(HtmlUtil.escape(comment.getCommentContent()));
        comment.setCommentAuthorName(user.getUserNickname());
        comment.setCommentAuthorEmail(user.getUserEmail());
        comment.setCommentAuthorUrl(user.getUserUrl());

        /*插入同时更新文章评论数，插入与更新放到同一个事务中，如果失败则全部回滚并报错*/
        try{
            commentService.insertCommentAndUpdateArticleCommentCount(comment);
        }catch(Exception e){
            return new JsonResult().fail().add("failMsg","插入失败");
        }

        /*返回JsonResult*/
        return new JsonResult().ok();
    }

    @RequestMapping(value = "/checkUserIfLogin")
    @ResponseBody
    public JsonResult commentCheckUserIfLogin(HttpSession httpSession){
        if(httpSession.getAttribute("user")==null){
            return new JsonResult().fail().add("msg","请先登录");
        }
        return new JsonResult().ok();
    }


}
