<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>

<html lang="zh-CN">
<head>
    <rapid:block name="title">
    </rapid:block>
</head>

<body>

    <div id="ContentHead">
        <a href="/admin"> 博客后台 </a>
        <a href="/"> 前往前台 </a>
        <a href="/logout"> 退出登录 </a>
    </div>
    <br/>

    <c:choose>
        <c:when test="${isADMIN==1}">
            <div id="menu">
                <a href="/admin/comment">  管理所有人的评论  </a>
                <div id="article-manage">
                    <a href="javascript:void(0)" id="article-manage-btn">  文章管理  </a>
                    <ul id="article-manage-total">
                        <%--待实现--%>
                        <li><a href="/">发表文章</a></li>
                        <li><a href="/admin/article">管理所有人的文章</a></li>
                        <li><a href="/">全部标签</a></li>
                        <li><a href="/">全部分类</a></li>
                    </ul>
                </div>
                <a href="javascript:void(0)" id="user-manage-btn">  用户管理  </a>
            </div>
        </c:when>
        <c:otherwise>
            <div id="menu">
                <a href="/admin/comment">  管理我的评论  </a>
                <a href="/admin/article">  管理我的文章  </a>
            </div>
        </c:otherwise>
    </c:choose>
    <br/><br/>

<rapid:block name="HTMLContent">

</rapid:block>

<script type="text/javascript" src="/js/jquery-1.12.4.min.js"></script>
<script>
    var articleManageTotalIsHide = 1;
    $(function () {
        $("#article-manage-total").hide();
    })

    $("#article-manage-btn").click(function () {
        if(articleManageTotalIsHide==1){
            articleManageTotalIsHide=0;
            $("#article-manage-total").show();
        }else{
            articleManageTotalIsHide=1;
            $("#article-manage-total").hide();
        }
        return false;
    })
</script>
<rapid:block name="pageScript">
</rapid:block>

</body>
</html>
