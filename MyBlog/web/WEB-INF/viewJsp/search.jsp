<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 19228
  Date: 2021/9/28
  Time: 19:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <ul>
        <div id="search-article-list">
            <c:forEach items="${searchArticleList}" var="a">
                <li><a href="/article/${a.articleId}">${a.articleTitle}</a></li>
            </c:forEach>
        </div>
    </ul>

</body>
</html>
