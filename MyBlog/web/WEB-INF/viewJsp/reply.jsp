<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<rapid:override name="title">
    <title>回复评论</title>
</rapid:override>

<rapid:override name="HTMLContent">
    <div id="Content-to-be-replied">
        原内容：${comment.commentContent}
    </div>
    <form id="reply-form">
        回复：
        <input id="commentPname" type="hidden" name="commentPname" value="${comment.commentAuthorName}">
        <input type="hidden" name="commentPid" value="${comment.commentPid==0? comment.commentId : comment.commentPid}">
        <input type="hidden" name="commentArticleId" value="${comment.commentArticleId}">
        <input type="text" name="commentContent" placeholder="请输入你的回复">
        <button id="reply-btn">回复</button>
    </form>

</rapid:override>

<rapid:override name="pageScript">
    <script>
        $("#reply-btn").click(function () {
            $.ajax({
                url: "/admin/comment",
                type: "POST",
                data: $("#reply-form").serialize(),
                dataType:"json",
                success:function (data) {
                    if(data.code==0){

                    }else{
                        window.location.href="/admin/comment";
                    }
                    return false;
                }
            })
            //Note:注意return false
            return false;
        });
    </script>
</rapid:override>

<%@ include file="/WEB-INF/viewJsp/admin/adminFramework.jsp" %>
