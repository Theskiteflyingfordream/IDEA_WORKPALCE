package com.ljw.blog.controller.admin;

import com.github.pagehelper.PageInfo;
import com.ljw.blog.dto.ArticleParam;
import com.ljw.blog.dto.JsonResult;
import com.ljw.blog.entity.*;
import com.ljw.blog.enums.ArticleStatus;
import com.ljw.blog.enums.UserRole;
import com.ljw.blog.service.ArticleService;
import com.ljw.blog.service.CategoryService;
import com.ljw.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/admin/article")
public class BackArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String dipMyArticlesPages(@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                     @RequestParam(required = false) String status,
                                     Model model,
                                     HttpSession httpSession){
        Map<String,Object> criteria = new HashMap();
        if(status==null){
            model.addAttribute("urlPrefix", "/admin/article?pageIndex=");
        }else{
            criteria.put("status", status);
            model.addAttribute("urlPrefix", "/admin/article?status=" + status + "&pageIndex=");
        }
        model.addAttribute("status",status);
        User currentUser = (User)httpSession.getAttribute("user");
        if(!currentUser.getUserRole().equals(UserRole.ADMIN.getValue())) criteria.put("userId",currentUser.getUserId());
        try{
            criteria.put("similar",1);
            PageInfo<Article> pageInfo = articleService.getArticlesByCriteriaWithPages(pageIndex,pageSize,criteria);
            model.addAttribute("pageInfo",pageInfo);
        }catch (Exception e){
            return "redirect:400";
        }
        return "/admin/article";
    }

    //接收到文章编辑的请求
    @RequestMapping(value = "/edit/{articleId}")
    public String getEditRequest(@PathVariable(value = "articleId") Integer articleId,
                                 HttpSession session,
                                 Model model){
        try{
            User currentUser = (User)session.getAttribute("user");
            Map<String,Object> criteria = new HashMap<>();
            criteria.put("articleId",articleId);
            Article articleFromDataBase = articleService.getArticlesByCriteriaWithoutPages(criteria).get(0);
            if(currentUser==null || articleFromDataBase==null || (!currentUser.getUserRole().equals(UserRole.ADMIN.getValue())&&!currentUser.getUserId().equals(articleFromDataBase.getArticleUserId()))){
                return "redirect:/403";
            }
            List<Category> categoryList = categoryService.getCategoryList();
            List<Tag> tagList = tagService.getAllTags();
            model.addAttribute("article",articleFromDataBase);
            model.addAttribute("categoryList",categoryList);
            model.addAttribute("tagList",tagList);
            return "/admin/editArticle";
        }catch (Exception e){
            return "redirect:/400";
        }
    }

    //待解决：当为发布时，内容，文章分类，文章标签均不能空；当为草稿时可以空
    //Note:用List<Tag> tagList接收前端数据，会因为无法convert而无法访问，前端传来的只是String，可以转换为Integer
    //Note:注意此处如果分类为一级分类，前端中的value=“”，则后端接收到的Integer categoryParent为null而不是0，如果为String categoryParent，则为；如果标签中没有东西选中，后端接收的tagList不是size为0，而是为null
    //Note:如果pojo中属性和@RequestParam的value同名，优先后者
    //点击编辑提交编辑后的文件
    @RequestMapping(value = "/{articleId}", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResult editArticleSubmit(@Valid ArticleParam articleParam, BindingResult bindingResult,
                                        HttpSession session){
        JsonResult jsonResult = new JsonResult();
        Map<String,Object> jsonResultExtend = jsonResult.getExtend();
        try{
            //先校验用户
            User currentUser = (User)session.getAttribute("user");
            Article articleFromDB = articleService.getSingleArticleById(articleParam.getArticleId());
            if(currentUser==null || (!currentUser.getUserRole().equals(UserRole.ADMIN.getValue()))&&currentUser.getUserId()!=articleFromDB.getArticleUserId()){
                jsonResultExtend.put("msg","非法访问");
                return jsonResult;
            }
            //然后校验文章状态
            List<FieldError> statusErrors = bindingResult.getFieldErrors("articleStatus");
            if(statusErrors.size()!=0){
                jsonResultExtend.put("articleStatus","请填入正确的文章状态");
                return jsonResult;
            }
            //根据文章状态校验其他字段
            if((articleParam.getArticleStatus() == ArticleStatus.Publish.getValue()) && ((bindingResult.hasErrors()) || articleParam.getCategoryParent()==null && articleParam.getCategoryChild()==null)){
                List<FieldError> errors = bindingResult.getFieldErrors();
                for (FieldError f : errors) {
                    jsonResultExtend.put(f.getField(),f.getDefaultMessage());
                }
                if(articleParam.getCategoryParent()==null && articleParam.getCategoryChild()==null){
                    jsonResultExtend.put("articleCategory", "文章分类不能为空");
                }
                return jsonResult;
            }
            //更新数据库
            articleService.updateArticleDetail(articleParam,true,true);
            return new JsonResult().ok().add("msg","保存成功");
        }catch (Exception e){
            //系统出错
            jsonResultExtend.put("msg","系统异常");
            return jsonResult;
        }
    }

    //删除文章
    @ResponseBody
    @RequestMapping(value = "/{articleId}",method = RequestMethod.DELETE)
    public JsonResult deleteArticle(@PathVariable(value = "articleId") Integer articleId, HttpSession session){
        try {
            Article articleFromDB = articleService.getSingleArticleById(articleId);
            User currentUser = (User)session.getAttribute("user");
            if(articleFromDB==null){
                return new JsonResult().fail().add("msg","非法删除！当前文章不存在");
            }
            if(!currentUser.getUserRole().equals(UserRole.ADMIN.getValue()) && !currentUser.getUserId().equals(articleFromDB.getArticleUserId())){
                return new JsonResult().fail().add("msg","非法删除！当前登录用户不是文章的作者");
            }
            articleService.deleteArticleAndRef(articleId);
        }catch (Exception e) {
            return new JsonResult().fail().add("msg","无法删除！系统异常");
        }
        return new JsonResult().ok().add("msg","删除成功");
    }

}
