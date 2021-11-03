<%--
  Created by IntelliJ IDEA.
  User: 19228
  Date: 2021/9/15
  Time: 11:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title></title>
    <%--<link href="/plugin/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet" />
    <script src="/plugin/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>--%>
    <%
        pageContext.setAttribute("APP_PATH", request.getContextPath());
    %>
</head>

<body>

    <div id="prjIfLoginAndRegister">
        <c:choose>
            <c:when test="${sessionScope.user==null}">
                <a href="/login">登录</a>
                /
                <a href="/register">注册</a>
            </c:when>
            <c:otherwise>
                <a href="/admin">进入后台</a>
                /
                <a id="logout" href="javascript:void(0);">退出登录</a>
            </c:otherwise>
        </c:choose>
    </div>

    <div id="BasicStatisticsShow">
        <text>网站概况</text>
        <text>文章总数:${basicStatistics.articleCount}</text>
        <text>留言数量:${basicStatistics.commentCount}</text>
        <text>分类总数:${basicStatistics.categoryCount}</text>
        <text>浏览总量:${basicStatistics.viewCount}</text>
        <text><fmt:formatDate value="${basicStatistics.articleUpdateTime}" pattern="yyyy年MM月dd日"/></text>
    </div>

    <div id="head">
        <table>
            <tr>
            <c:forEach items="${categoryList}" var="c">
                <c:if test="${c.categoryPid == 0}">
                    <td><a href="/category/${c.categoryId}" class="parCategory" id="${c.categoryId}">${c.categoryName}</a></td>
                    <ul class="sonCategory" id="sonCategory-${c.categoryId}">
                        <c:forEach items="${categoryList}" var="sc">
                            <c:if test="${sc.categoryPid == c.categoryId}">
                                <li><a href="/category/${sc.categoryId}">${sc.categoryName}</a></li>
                            </c:if>
                        </c:forEach>
                    </ul>
                </c:if>
            </c:forEach>
            </tr>
        </table>
    </div>
    <br/>

    <div id="search">
        <%--NOTE：此处用ajax或者直接写action发送在服务器端取出数据上没有区别--%>
        <form id="search-form" method="get" action="${APP_PATH}/search">
            <input type="text" name="keywords" required>
            <button type="submit">搜索</button>
        </form>
    </div>

    <div id="articleList">
        <c:forEach items="${pageInfo.list}" var="a">
            <ul>
                <li>
                    <a href="/article/${a.articleId}">
                            ${a.articleId}
                    </a>
                </li>
                <li>
                    <a href="/article/${a.articleId}">
                            ${a.articleTitle}
                    </a>
                </li>
                <li>
                    <a href="/article/${a.articleId}">
                            ${a.articleSummary}
                    </a>
                </li>
                <li><a href="/article/${a.articleId}#comment-area"/>点击此处跳转到该文章的评论区</li>
            </ul>
        </c:forEach>
            <%@ include file="/WEB-INF/viewJsp/common/paging.jsp"%>
    </div>

    <div id="latestTenCommentShow">
        <text>近期评论</text>
        <br/>
        <c:forEach items="${basicStatistics.latestTenComment}" var="c">
            <a href="/article/${c.commentArticleId}#div-comment-${c.commentId}">
                ${c.commentAuthorName} ${c.commentContent}
                <br/>
            </a>
        </c:forEach>
    </div>

    <script type="text/javascript" src="/js/jquery-1.12.4.min.js"></script>
    <script src="/js/script.js"></script>
    <script type="text/javascript">
        $("#logout").click(function (){
            alert("退出登录成功");
            window.location.href="/logout";
        });
    </script>

</body>


</html>

