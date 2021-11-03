<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 19228
  Date: 2021/9/18
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${article.articleTitle}</title>
    <%--QUES：引入出错？--%>
   <%--<script type="text/javascript" src="/js/jquery.cookie.js"></script>--%>
    <style type="text/css">
        #article-content{
            border:10px solid black;;
        }
    </style>
</head>
<body>

<div id="article-view-and-like">
    <table align="right" cellspacing="20">
        <tr>
            <td>    文章浏览量：<text id="articleViewCount">${article.articleViewCount}</text>   </td>
            <td>    文章点赞量：<text id="articleLikeCount">${article.articleLikeCount}</text>    </td>
        </tr>
    </table>
</div>

<div id="articleCategory">
    <table align="left" cellspacing="10">
        <tr>
            <td>    <a href="/">首页></a>    </td>
            <c:choose>
                <c:when test="${article.categoryList != null && article.categoryList.size() > 0}">
                    <c:forEach items="${article.categoryList}" var="acg">
                        <td>    <a href="/category/${acg.categoryId}">${acg.categoryName} ></a>    </td>
                    </c:forEach>
                    <td>    <a href="/article/${article.articleId}#article-content">正文</a>    </td>
                </c:when>
                <c:otherwise>
                    <td>    未分类    </td>
                </c:otherwise>
            </c:choose>
        </tr>
    </table>

</div>

<br/>
<br/>

<div id="article-body" record-articleId = ${article.articleId}>
    <div>
        <main id = "article-content">${article.articleContent}</main>
        <br/>
        <%--NOTE：a标签使用onclick，链接的 onclick 事件被先执行，其次是 href 属性下的动作（页面跳转，或 javascript 伪链接），如果不想让href 属性下的动作执行，onclick 需要要返回 false ，一般是这样写onclick="xxx();return false;".--%>
        <a href="javascript:void(0)" onclick="increaseViewCountOrLikeCount(0)">点击此为该篇文章点赞</a>
    </div>
    <br/>
    <div>
        <c:choose>
            <c:when test="${preArticle!=null}">
                <a href="/article/${preArticle.articleId}">上一篇</a>
            </c:when>
            <c:otherwise>
                <text>没有上一篇了!</text>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${afterArticle!=null}">
                <a href="/article/${afterArticle.articleId}">下一篇</a>
            </c:when>
            <c:otherwise>
                <text>没有下一篇了!</text>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<br/>

<div id="similarArticleList">
    相似文章<br/>
    <ul>
        <c:forEach items="${similarArticleList}" var="a">
            <li> <a href="/article/${a.articleId}"> ${a.articleTitle} </a> </li>
        </c:forEach>
    </ul>
</div>

<div id="mostCommentArticleList">
    热评文章<br/>
    <ul>
        <c:forEach items="${mostCommentArticleList}" var="a">
            <li> <a href="/article/${a.articleId}"> ${a.articleTitle} </a> </li>
        </c:forEach>
    </ul>
</div>


<div id="comment-area">
    <c:if test="${sessionScope.user==null}">
        <span>
            您未登录，登录后才能评论,
        </span>
        <p>
            <a href="/login">点击此前往登录</a>
        </p>
    </c:if>
    <form id="comment-submit" >
        <input id="commentPid" type="hidden" name="commentPid" value="0">
        <input id="commentPname" type="hidden" name="commentPname" value="">
        <textarea id="comment" name="commentContent" rous="4" tabindex="1" required placeholder=""></textarea>
        <input type="hidden" name="commentArticleId" value="${article.articleId}">
        <input type="submit" value="发表评论">
    </form>
    <button id="cancel-reply" type="button">取消回复</button>

    <c:choose>
        <c:when test="${article.commentList.size()!=0}">
            <div id="comment-list">
                <text>评论列表</text>
                <c:set var="floor" value="0"></c:set>
                <c:forEach items="${article.commentList}" var="c">
                    <div class="commentBody">
                        <c:if test="${c.commentPid==0}">
                            <c:set var="floor" value="${floor+1}"></c:set>
                            <div id="div-comment-${c.commentId}" class="commentParent">
                                <c:if test="${sessionScope.user!=null && (isADMIN==1 || sessionScope.user.userId==c.commentUserId)}">
                                    <a  href="/admin/comment/edit/${c.commentId}" target="_blank">编辑</a>
                                    /
                                    <a href="javascript:void(0)" onclick="deleteComment(${c.commentId})">删除</a>
                                </c:if>
                                    <li>${floor}楼</li>
                                    <li>${c.commentCreateTime}</li>
                                    <li>${c.commentAuthorName}:</li>
                                    <li>${c.commentContent}</li>
                                    <a href="javascript:void(0)" id="${c.commentAuthorName}" class="replyComment-link">回复</a>
                                </ul>
                            </div>
                            <c:set var="floor2" value="0"></c:set>
                            <c:forEach items="${article.commentList}" var="ch">
                                <c:if test="${ch.commentPid == c.commentId}">
                                    <c:set var="floor2" value="${floor2+1}"></c:set>
                                    <div id="div-comment-${ch.commentId}">
                                        <c:if test="${sessionScope.user!=null && (isADMIN==1 || sessionScope.user.userId==ch.commentUserId)}">
                                            <a  href="/admin/comment/edit/${ch.commentId}" target="_blank">编辑</a>
                                            /
                                            <a href="javascript:void(0)" onclick="deleteComment(${ch.commentId})">删除</a>
                                        </c:if>
                                        <ul>
                                            <li>${floor2}楼</li>
                                            <li>@${ch.commentPname}</li>
                                            <li>${ch.commentAuthorName}:</li>
                                            <li>${ch.commentContent}</li>
                                            <a href="javascript:void(0)" id="${ch.commentAuthorName}" class="replyComment-link">回复</a>
                                        </ul>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </c:if>
                        <br/><br/>
                    </div>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <text>该篇文章还没有评论哦,快去发表吧!</text>
        </c:otherwise>
    </c:choose>


</div>



<%--Note:注意script文件的引入顺序--%>

<script type="text/javascript" src="/js/jquery-1.12.4.min.js"></script>
<script src="https://cdn.staticfile.org/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<script src="/js/script.js"></script>
<script>
    $(function () {
        $("#cancel-reply").hide();
    })

    //发表评论
    $("#comment-submit").submit(function () {
        $.ajax({
            async: false,
            type: "POST",
            url: "/comment",
            data:$("#comment-submit").serialize(),
            success: function (data) {
                if(data.code == 1){
                    alert("操作成功");
                    window.location.reload();
                    return false;
                }else{
                    alert(data.extend.failMsg);
                    return false;
                }
            }
        })
        return false;
    })

    /*浏览量加一*/
    var articleId = $("#article-body").attr("record-articleId");
    increaseViewCountOrLikeCount(1);

    /*点击回复按钮*/
    $(".replyComment-link").click(function () {
        //ajax,添加用户的登录判断，如果用户未登录，alert（“请先登录”）
        var prjWhetherLogin = 1;
        $.ajax({
            url:"/checkUserIfLogin",
            async:false,
            type:"GET",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            dataType: "json",
            success:function (data) {
                if(data.code==0){
                    prjWhetherLogin = 0;
                    alert(data.extend.msg);
                }
            }
        });
        if(prjWhetherLogin==0) return false;
        console.log(prjWhetherLogin);

        var commentPid = $(this).parents(".commentBody").find(".commentParent").attr("id").match(/\d+/g);
        $("#comment-submit").find("#commentPid").val(commentPid);
        $("#comment-submit").find("#commentPname").val($(this).attr("id"));
        $("#cancel-reply").show();
        $("#comment-submit").find("#comment").attr("placeholder","@"+$(this).attr("id"));
        //Note:无需填ip和端口也行
        window.location.href="/article/32#comment-submit"
    });

    //点击取消回复
    $("#cancel-reply").click(function () {
        $("#comment-submit").find("#commentPid").val("0");
        $("#comment-submit").find("#commentPname").val("");
        $("#cancel-reply").hide();
        $("#comment-submit").find("#comment").attr("placeholder","");
    })


</script>

</body>
</html>
