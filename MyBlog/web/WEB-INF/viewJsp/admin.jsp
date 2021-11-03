<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<rapid:override name="title">
    <title>后台</title>
</rapid:override>


<%--Note:rapid override外面可以用jstl判断--%>
<rapid:override name="HTMLContent">
    <div id="articleList">
        <p>最近发布</p>
        <ul>
            <c:forEach var="ra" items="${recentArticleList}">
                <li>
                    创建于
                    <fmt:formatDate value="${ra.articleCreateTime}" pattern="HH:mm YYYY年MM月dd日"/>
                    最近更新于
                    <fmt:formatDate value="${ra.articleUpdateTime}" pattern="HH:mm YYYY年MM月dd日"/>
                    title:<a href="/article/${ra.articleId}">${ra.articleTitle}</a>
                </li>
            </c:forEach>
        </ul>
    </div>

    <p>近期评论</p>
    <div id="commentList">
        <ul>
            <c:forEach var="rc" items="${recentCommentList}">
                <li>
                    <img src="${rc.user.userAvatar}" height="50" width="50">
                    <p>
                            ${rc.commentAuthorName}
                        于
                        <fmt:formatDate value="${rc.commentCreateTime}" pattern="HH:mm YYYY年MM月dd日"/>
                        在
                        <a href="/article/${rc.article.articleId}">${rc.article.articleTitle}</a>
                        <c:choose>
                            <c:when test="${rc.commentPid!=0}">
                                回复 @${rc.commentPname}
                            </c:when>
                            <c:otherwise>
                                发表评论
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <p>
                        评论内容：<a href="/article/${rc.article.articleId}/#div-comment-${rc.commentId}">${rc.commentContent}</a>
                    </p>
                </li>
            </c:forEach>
        </ul>
    </div>
</rapid:override>


<%@ include file="/WEB-INF/viewJsp/admin/adminFramework.jsp" %>
