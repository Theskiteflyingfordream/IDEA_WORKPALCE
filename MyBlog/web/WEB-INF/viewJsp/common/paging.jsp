<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  分页条逻辑
  User: 19228
  Date: 2021/9/17
  Time: 16:01
  To change this template use File | Settings | File Templates.
--%>

<%--注意jstl中test与mybatis中的区别,后者可以直接用"",后者需要加${}--%>
<c:if test="${pageInfo.pages > 1}">
    <c:choose>
        <c:when test="${pageInfo.pages <= 3}">
            <c:set var="begin" value="1" />
            <c:set var="end" value="${pageInfo.pages}" />
        </c:when>
        <c:otherwise>
            <c:set var="begin" value="${pageInfo.pageNum - 1}" />
            <c:set var="end" value="${pageInfo.pageNum + 1}" />
            <c:if test="${pageInfo.pageNum == 1}">
                <c:set var="end" value="3"></c:set>
            </c:if>
            <c:if test="${pageInfo.pageNum == pageInfo.pages}">
                <c:set var="begin" value="${pageInfo.pageNum - 2}"></c:set>
            </c:if>
            <c:if test="${begin < 1}">
                <c:set var="begin" value="1" />
            </c:if>
            <c:if test="${end > pageInfo.pages}">
                <c:set var="end" value="${pageInfo.pages}" />
            </c:if>
        </c:otherwise>
    </c:choose>
    <c:if test="${pageInfo.pageNum != 1}">
        <a href="${urlPrefix}${pageInfo.pageNum - 1}">上一页</a>
    </c:if>

    <c:forEach begin="${begin}" end="${end}" var="n">
        <a href="${urlPrefix}${n}">${n}</a>
    </c:forEach>

    <c:if test="${pageInfo.pageNum != pageInfo.pages}">
        <a href="${urlPrefix}${pageInfo.pageNum + 1}">下一页</a>
    </c:if>

</c:if>
