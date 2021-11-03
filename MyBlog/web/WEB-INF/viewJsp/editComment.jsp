<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<rapid:block name="title">
    <title>评论修改</title>
</rapid:block>

<rapid:override name="HTMLContent">

    <div id="edit-comment">
        <p>用户昵称：${comment.commentAuthorName}</p>
        <p>创建日期：${comment.commentCreateTime}</p>
        <c:if test="${comment.commentPid!=0}">
            <p>回复：@${comment.commentPname}</p>
        </c:if>
        <form id="comment-edit-form">
            <input type="text" name="commentContent" value="${comment.commentContent}" >
            <button type="submit" id="${comment.commentId}" class="edit-submit-btn" >提交修改</button>
        </form>
    </div>
</rapid:override>


<rapid:override name="pageScript">
    <script src="/js/script.js"></script>
    <script>
        $(".edit-submit-btn").click(function () {
            $.ajax({
                async: false,
                type: "PUT",
                url: "/admin/comment/"+$(this).attr("id"),
                data:$("#comment-edit-form").serialize(),
                success: function (data) {
                    alert(data.extend.msg);
                    if(data.code == 1) {
                        window.location.href="/admin/comment";
                    }
                }
            })
            return false;
        })
    </script>
</rapid:override>


<%@ include file="/WEB-INF/viewJsp/admin/adminFramework.jsp" %>
