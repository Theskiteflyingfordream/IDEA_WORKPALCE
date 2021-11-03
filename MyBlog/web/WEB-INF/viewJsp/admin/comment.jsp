<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<rapid:override name="title">
    <title>评论管理</title>
</rapid:override>

<rapid:override name="HTMLContent">
    <ul>
        <c:forEach items="${pageInfo.list}" var="c">
            <li>
                <a  href="/admin/comment/edit/${c.commentId}" target="_blank">编辑</a>
                /
                <a href="javascript:void(0)" onclick="deleteComment(${c.commentId})">删除</a>
                /
                <a href="/admin/comment/reply/${c.commentId}" target="_blank">回复</a>
                <p>作者：${c.commentAuthorName}</p>
                <p>评论ID：${c.commentId}</p>
                <p>评论所属的文章：<a href="/article/${c.article.articleId}">${c.article.articleTitle}</a></p>
                <c:if test="${c.commentPid!=0}">
                    回复：@${c.commentPname}
                </c:if>
                <p>评论内容：<a href="/article/${c.article.articleId}/#div-comment-${c.commentId}">${c.commentContent}</a></p>
            </li>
        </c:forEach>
    </ul>
    <%@ include file="/WEB-INF/viewJsp/common/paging.jsp"%>
</rapid:override>

<rapid:override name="pageScript">
    <script src="/js/script.js"></script>
</rapid:override>

<%@ include file="/WEB-INF/viewJsp/admin/adminFramework.jsp" %>

