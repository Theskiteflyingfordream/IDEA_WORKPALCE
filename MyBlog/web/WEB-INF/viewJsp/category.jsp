<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 19228
  Date: 2021/9/23
  Time: 16:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${category.categoryName}</title>
</head>
<body>
    <ul>
        <c:forEach items="${category.articleList}" var="a">
            <li>
                <a href="/article/${a.articleId}">${a.articleTitle}</a>
            </li>
            <br/>
        </c:forEach>
    </ul>

</body>
</html>
