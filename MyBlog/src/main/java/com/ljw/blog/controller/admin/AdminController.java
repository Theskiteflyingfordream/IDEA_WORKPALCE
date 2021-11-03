package com.ljw.blog.controller.admin;

import com.ljw.blog.dto.JsonResult;
import com.ljw.blog.entity.User;
import com.ljw.blog.enums.UserRole;
import com.ljw.blog.service.ArticleService;
import com.ljw.blog.service.CommentService;
import com.ljw.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName AdminController
 * @Description TODO
 * @DATE 2021/9/28 21:38
 */

/*Note:@RequestMapping等路径均需要考虑工程名*/
@Controller
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    ArticleService articleService;

    @Autowired
    CommentService commentService;

    @RequestMapping(value = "/admin")
    public String toAdminJsp(HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        Integer userId = null;
        if(user.getUserRole().equals(UserRole.USER.getValue())) userId=user.getUserId();
        Map<String,Object> criteria = new HashMap<>();
        /*获取当前用户的自己最近发表的5篇文章，管理员则获取最近发表的5篇*/
        criteria.put("userId",userId);
        criteria.put("limit",5);
        criteria.put("status",1);
        model.addAttribute("recentArticleList",articleService.getArticlesByCriteriaWithoutPages(criteria));
        /*获取最近5条评论*/
        model.addAttribute("recentCommentList",commentService.getCommentsByCriteria(criteria));
        return "admin";
    }

    @RequestMapping(value = "/login")
    public String toLoginJsp() { return "login"; }

    @RequestMapping(value = "/register")
    public String toRegisterJsp(){
        return "register";
    }

    /*Note:如果return不带forward或者redirect，则根据视图解析器和contextPath拼串；否则直接和contextPath拼串*/
    @RequestMapping(value = "/logout")
    public String logout(HttpSession session,HttpServletResponse response,HttpServletRequest request) throws IOException {
        session.removeAttribute("user");
        session.invalidate();
        return "forward:/";
    }

    @RequestMapping(value = "/loginVerify")
    @ResponseBody
    public JsonResult loginVerify(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        String rememberme = request.getParameter("rememberme");
        String userPass = request.getParameter("userPass");
        String userNameOrEmail = request.getParameter("userName");
        JsonResult jsonResult = new JsonResult();
        Map<String,Object> jsonResultExtend = jsonResult.getExtend();
        User userFromDataBase = null;
        /*根据userName是否包含@区分用户名与邮箱，并进行校验*/
        if(userNameOrEmail.contains("@")) {
            if(!userNameOrEmail.matches("(^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$)")) {
                jsonResultExtend.put("userName","邮箱格式不正确");
            }else if((userFromDataBase=userService.getUserByUserEmail(userNameOrEmail))==null) {
                jsonResultExtend.put("userName","邮箱未注册用户");
            }
        }else {
            if(!userNameOrEmail.matches("(^[a-zA-Z0-9_-]{3,16}$)")){
                jsonResultExtend.put("userName","用户名必须是3-16位英文或数字的组合");
            }else if((userFromDataBase=userService.getUserByUserName(userNameOrEmail))==null){
                jsonResultExtend.put("userName","用户名未注册用户");
            }
        }
        /*校验密码*/
        if(userFromDataBase!=null && userFromDataBase.getUserStatus()==0) {
            jsonResultExtend.put("userName","账号已被注销");
        }
        if(!userPass.matches("(^[a-zA-Z0-9_-]{6,18}$)")) {
            jsonResultExtend.put("userPass","密码必须是6-18位的英文或者数字或者_-或其组合");
        }else if(userFromDataBase!=null && !userPass.equals(userFromDataBase.getUserPass())){
            jsonResultExtend.put("userPass","密码错误");
        }
        /*存在失败则直接返回*/
        if(jsonResultExtend.size() != 0){
            jsonResult.setCode(0);
            jsonResult.setMsg("操作失败");
            return jsonResult;
        }
        /*校验均成功*/
        session.setAttribute("user",userFromDataBase);
        /*Note:注意如果传入的rememberme为空，则判断rememberme==1会报空指针异常*/
        if(rememberme!=null){
            /*设置cookie并保存*/
            Cookie nameCookie = new Cookie("username",userFromDataBase.getUserName());
            nameCookie.setMaxAge(60 * 60 * 24 * 3);
            Cookie pwdCookie = new Cookie("userpass",userFromDataBase.getUserPass());
            pwdCookie.setMaxAge(60 * 60 * 24 * 3);
            response.addCookie(nameCookie);
            response.addCookie(pwdCookie);
        }
        /*更新user中的最近登录日期和最近登录Ip*/
        userFromDataBase.setUserLastLoginTime(new Date());
        userService.updateUserByUserName(userFromDataBase);
        return new JsonResult().ok();
    }

    @ResponseBody
    @RequestMapping(value = "/checkUserName")
    public JsonResult checkUserName(@RequestParam(value = "userName") String userName){
        String regx = "^[a-zA-Z0-9_-]{3,16}$";
        if(!userName.matches(regx)){
            return new JsonResult().fail().add("unMsg","用户名必须是3-16位数字和字母的组合");
        }
        if(userService.getUserByUserName(userName) != null){
            return new JsonResult().fail().add("unMsg","用户名已存在");
        }
        return new JsonResult().ok().add("unMsg","用户名可用");
    }

    @ResponseBody
    @RequestMapping(value = "/checkUserEmail")
    public JsonResult checkUserEmail(@RequestParam(value = "userEmail") String userEmail){
        String regx = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";
        if(!userEmail.matches(regx)){
            return new JsonResult().fail().add("ueMsg","邮箱格式不正确");
        }
        if(userService.getUserByUserEmail(userEmail) != null){
            return new JsonResult().fail().add("ueMsg","邮箱已被注册");
        }
        return new JsonResult().ok().add("ueMsg","邮箱可用");
    }

    /*QUES:将用户密码加密再存入数据库？*/
    /*Note:注意BindingResult必须紧跟@Valid后面，否则无法封装错误，直接报错；如果有多个@Valid，则依次再其后添加BindingResult参数，如,@Valid User user, BindingResult result*/
    @ResponseBody
    @RequestMapping(value = "/registerVarify")
    public JsonResult registerVarify(@Valid User user,BindingResult bindingResult,@RequestParam(value = "confirmUserPass",required = false) String confirmUserPass){
        JsonResult jsonResult = new JsonResult();
        Map<String,Object> jsonResultExtend = jsonResult.getExtend();
        /*将JSR303校验结果放入jsonResult中*/
        if(!user.getUserPass().equals(confirmUserPass)) jsonResultExtend.put("confirmUserPass","密码不一致");
        if(bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError f : errors) {
                jsonResultExtend.put(f.getField(),f.getDefaultMessage());
            }
        }
        /*判断用户名与邮箱是否已被使用*/
        if(!jsonResultExtend.containsKey("userName") && userService.getUserByUserName(user.getUserName()) != null){
            jsonResultExtend.put("userName","用户名已存在");
        }
        if(!jsonResultExtend.containsKey("userEmail") && userService.getUserByUserEmail(user.getUserEmail()) != null){
            jsonResultExtend.put("userEmail","邮箱已被注册");
        }
        if(jsonResultExtend.size()!=0){
            jsonResult.setCode(0);
            jsonResult.setMsg("操作失败");
            return jsonResult;
        }
        /*用户的输入都正确*/
        user.setUserAvatar("/img/avatar/avatar.png");
        user.setUserStatus(1);
        user.setArticleCount(0);
        user.setUserRole(UserRole.USER.getValue());
        try{
            userService.insertUser(user);
        }catch (Exception e){
            return jsonResult.fail().add("sysError","系统异常");
        }
        return jsonResult.ok();
    }

}
