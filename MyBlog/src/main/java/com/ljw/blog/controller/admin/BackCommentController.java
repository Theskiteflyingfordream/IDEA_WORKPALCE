package com.ljw.blog.controller.admin;

import cn.hutool.http.HtmlUtil;
import cn.hutool.http.HttpRequest;
import com.github.pagehelper.PageInfo;
import com.ljw.blog.dto.JsonResult;
import com.ljw.blog.entity.Article;
import com.ljw.blog.entity.Comment;
import com.ljw.blog.entity.User;
import com.ljw.blog.enums.Role;
import com.ljw.blog.enums.UserRole;
import com.ljw.blog.service.ArticleService;
import com.ljw.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName BackCommentController
 * @Description TODO
 * @DATE 2021/10/19 21:34
 */
@Controller
@RequestMapping("/admin/comment")
public class BackCommentController {
    @Autowired
    CommentService commentService;

    @Autowired
    private ArticleService articleService;


    //在后台分页显示我的评论
    @RequestMapping(value = {""},method = RequestMethod.GET)
    public String dipMyCommentByPages(@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                          @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                                          Model model,
                                      HttpSession httpSession){
        User currentUser = (User) httpSession.getAttribute("user");
        if (currentUser==null){
            return "/403";
        }
        Integer userId = null;
        if(!currentUser.getUserRole().equals(UserRole.ADMIN.getValue())){
            userId=currentUser.getUserId();
        }
        PageInfo<Comment> commentPageInfo = commentService.getCommentsByUserWithPages(pageIndex,pageSize,userId);
        model.addAttribute("pageInfo",commentPageInfo);
        model.addAttribute("urlPrefix","/admin/comment?pageIndex=");
        return "/admin/comment";
    }

    //发表评论或用户提交回复的评论
    @RequestMapping(value = {""},method = RequestMethod.POST)
    @ResponseBody
    public JsonResult submitComment(Comment comment, HttpSession session){
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

    //删除评论
    @ResponseBody
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public JsonResult deleteComment(@PathVariable(value = "id") Integer commentId, HttpSession session){
        Comment commentFromDataBase = commentService.getCommentByCommentId(commentId);
        User currentUser = (User)session.getAttribute("user");
        if(currentUser==null){
            return new JsonResult().fail().add("msg","当前尚未登录用户，请登录");
        }
        if(commentFromDataBase==null){
            return new JsonResult().fail().add("msg","非法删除！当前评论不能存在");
        }
        if(!currentUser.getUserRole().equals(UserRole.ADMIN.getValue()) && !currentUser.getUserId().equals(commentFromDataBase.getCommentUserId())){
            return new JsonResult().fail().add("msg","非法删除！当前登录用户不是评论的作者");
        }
        try {
            commentService.deleteCommentAndUpdateArticleCommentCount(commentId,commentFromDataBase.getCommentArticleId());
        }catch (Exception e) {
            return new JsonResult().fail().add("msg","无法删除！系统异常");
        }
        return new JsonResult().ok().add("msg","删除成功");
    }

    //用户提交编辑好的评论
    @RequestMapping(value = "/{commentId}",method = RequestMethod.PUT)
    @ResponseBody
    public JsonResult editedCommentSubmit(Comment comment,HttpSession httpSession){
        User currentUser = (User)httpSession.getAttribute("user");
        Comment commentFromDataBase = commentService.getCommentByCommentId(comment.getCommentId());
        if(commentFromDataBase==null){
            return new JsonResult().fail().add("msg","当前评论不存在");
        }else if(currentUser==null){
            return new JsonResult().fail().add("msg","请先登录");
        }else if(!currentUser.getUserRole().equals(UserRole.ADMIN.getValue()) && !currentUser.getUserId().equals(commentFromDataBase.getCommentUserId())){
            return new JsonResult().fail().add("msg","非法修改当前评论");
        }
        try{
            commentService.updateComment(comment);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new JsonResult().fail().add("msg","修改无法提交！系统异常");
        }
        return new JsonResult().ok().add("msg","修改提交成功");
    }

    //Note：return后如果不带/，默认添加拼串后，将所得在当前地址后添加
    //收到编辑请求，审核并转到编辑页面
    @RequestMapping(value = "/edit/{commentId}")
    public String getEditRequest(@PathVariable(value = "commentId") Integer commentId,
                                     HttpSession httpSession,
                                     Model model){
        try{
            Comment commentFromDataBase = commentService.getCommentByCommentId(commentId);
            User currentUser = (User)httpSession.getAttribute("user");
            if(currentUser==null || commentFromDataBase==null || (!currentUser.getUserRole().equals(UserRole.ADMIN.getValue()) && !currentUser.getUserId().equals(commentFromDataBase.getCommentUserId()))){
                return "redirect:/403";
            }
            model.addAttribute("comment",commentFromDataBase);
            return "editComment";
        }catch (Exception e) {
            return "redirect:/400";
        }
    }

    //收到回复用户评论的请求
    @RequestMapping(value = "/reply/{commentId}")
    public String getReplyRequest(@PathVariable(value = "commentId") Integer commentId,
                                  HttpSession httpSession,
                                  Model model){
        try{
            Comment commentFromDataBase = commentService.getCommentByCommentId(commentId);
            User currentUser = (User)httpSession.getAttribute("user");
            if(currentUser==null || commentFromDataBase==null || (!currentUser.getUserRole().equals(UserRole.ADMIN.getValue()) && !currentUser.getUserId().equals(commentFromDataBase.getCommentUserId()))){
                return "redirect:/403";
            }
            model.addAttribute("comment",commentFromDataBase);
            return "reply";
        }catch (Exception e) {
            return "redirect:/400";
        }
    }


}
