<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<rapid:override name="title">
    <title>文章管理</title>
</rapid:override>

<rapid:override name="HTMLContent">
    <div id="total-article">
        <%--待解决：选中下拉列表修改urlPrefix--%>
        <div>
            文章状态：
            <select id="selectStatus">
                <option <c:if test="${status==1}">  selected </c:if> id="published-selection">已发布</option>
                <option <c:if test="${status==0}">  selected </c:if> id="craft-selection">草稿</option>
                <option <c:if test="${status==null}">  selected </c:if> selected="selected" id="all-selection">全部</option>
            </select>
        </div>
        <table id="article-table" border="1">
            <tr>
                <th>用户</th>
                <th>文章ID</th>
                <th>标题</th>
                <th>所属分类</th>
                <th>状态</th>
                <th>发布时间</th>
                <th>最近修改时间</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${pageInfo.list}" var="a">
                <tr>
                    <td>${a.user.userNickname}</td>
                    <td>${a.articleId}</td>
                    <td><a href="/article/${a.articleId}">${a.articleTitle}</a></td>
                    <td>
                        <c:choose>
                            <%--Note:使用collection联合查询查出来如果List为空，则其.size()=0，而不是List为null--%>
                            <c:when test="${a.categoryList==null || a.categoryList.size()==0}">
                                未分类
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${a.categoryList}" var="ac">
                                    <a href="/category/${ac.categoryId}">${ac.categoryName}</a>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${a.articleStatus==1}">已发布</c:when>
                            <c:otherwise>草稿</c:otherwise>
                        </c:choose>
                    </td>
                    <td><fmt:formatDate value="${a.articleCreateTime}" pattern="YYYY-MM-dd HH:mm:ss"/></td>
                    <td><fmt:formatDate value="${a.articleUpdateTime}" pattern="YYYY-MM-dd HH:mm:ss"/></td>
                    <td>
                        <a href="/admin/article/edit/${a.articleId}">编辑</a>
                        <a href="javascript:void(0);" id="${a.articleId}" class="article-delete-btn">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <%@ include file="/WEB-INF/viewJsp/common/paging.jsp"%>
</rapid:override>

<rapid:override name="pageScript">
    <script src="/js/script.js"></script>
    <script>
        $(".article-delete-btn").click(function () {
            if(confirmDelete()){
                $.ajax({
                    url: "/admin/article/"+$(this).attr("id"),
                    type: "DELETE",
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    dataType: "json",
                    success:function (data) {
                        alert(data.extend.msg);
                        if(data.code==0){
                            return false;
                        }else{
                            window.location.reload();
                            return false;
                        }
                    }
                });
            }
        })

        $("#selectStatus").change(function () {
            var urlNew = "/admin/article?";
            if($("#published-selection").is(":checked")){
                urlNew += "status=1";
            }else if($("#craft-selection").is(":checked")){
                urlNew += "status=0";
            }

            window.location.href = urlNew;

        })

        /*$(function () {
            if($("#published-selection").is(":checked")){

            }else if($("#craft-selection").is(":checked")){

            }else{

            }
        })*/

    </script>
</rapid:override>



<%@ include file="/WEB-INF/viewJsp/admin/adminFramework.jsp" %>
