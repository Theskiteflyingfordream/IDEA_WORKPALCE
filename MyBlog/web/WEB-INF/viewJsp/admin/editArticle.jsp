<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<rapid:override name="title">
    <title>文章编辑</title>
</rapid:override>

<rapid:override name="HTMLContent">
    <form id="article-edit-form">
        标题：
        <input id="articleTitle" name="articleTitle" type="text" value="${article.articleTitle}">
        <br/>
        内容：
        <textarea id="articleContent" name="articleContent" type="text" value="${article.articleContent}"></textarea>
        <br/>
        分类：
        <div id="articleCategory">
            <select name="categoryParent" id="parent-category-select">
                <option value="">一级分类</option>
                <c:forEach items="${categoryList}" var="cl">
                    <c:if test="${cl.categoryPid==0}">
                        <%--Note:jstl可以放在jsp任何地方使用--%>
                        <option value="${cl.categoryId}" <c:if test="${article.categoryList[0].categoryId==cl.categoryId}"> <c:set var="parentArticleId" value="${cl.categoryId}"/> selected</c:if> >${cl.categoryName}</option>
                    </c:if>
                </c:forEach>
            </select>
            <select name="categoryChild" id="child-category-select">
                <c:forEach items="${categoryList}" var="cc">
                    <c:if test="${cc.categoryPid==parentArticleId}">
                        <option value="${cc.categoryId}"
                                <c:if test="${article.categoryList[1].categoryId == cc.categoryId}">selected</c:if>>${cc.categoryName}
                        </option>
                    </c:if>
                </c:forEach>
            </select>
        </div>
        <br/>
        标签：
        <div id="tagList">
            <%--Note:后端在articleTagIds使用List接收--%>
            <c:forEach items="${tagList}" var="t">
                <input type="checkbox" name="articleTagIds" title="${t.tagName}"
                       value="${t.tagId}"
                <c:forEach items="${article.tagList}" var="t2">
                       <c:if test="${t.tagId == t2.tagId}">checked</c:if>
                </c:forEach>>${t.tagName}
            </c:forEach>
        </div>
        <br/>
        状态：
        <div id="status">
            <input type="radio" name="articleStatus" value="1" <c:if test="${article.articleStatus==1}">checked</c:if>>发布
            <input type="radio" name="articleStatus" value="0" <c:if test="${article.articleStatus==0}">checked</c:if>>草稿
        </div>
        <br/>
        <%--Note:不选择type默认submit--%>
        <div id="submit-or-init">
            <button type="button" id="submit">提交</button>
            <button type="button" id="init">重置</button>
        </div>
    </form>
</rapid:override>

<rapid:override name="pageScript">
    <script>
        
        //一二级分类联动
        $("#parent-category-select").change(function () {
            var str = "";
            var parentCategoryId = $("#parent-category-select").val();
            <c:forEach items="${categoryList}" var="c">
            //Note:js中空串在判断逻辑中，与0相等
            if (parentCategoryId ==${c.categoryPid}) {
                str += "<option name='childCategory' value='${c.categoryId}'<c:if test='${article.categoryList[1].categoryId == c.categoryId}'>selected</c:if>>${c.categoryName}</option>";
            }
            </c:forEach>
            $("#child-category-select").html("<option value=''selected>二级分类</option>" + str);
        })
        
        //点击保存
        $("#submit").click(function () {
            $.ajax({
                async: false,
                url: "/admin/article/"+${article.articleId},
                type: "PUT",
                data: $("#article-edit-form").serialize(),
                success: function (data) {
                    /*Note:extend为一个json对象时，使用.运算和[]均能取出对应的属性值,如alert(data.extend["msg"]);*/
                    if(data.extend.msg != undefined){
                        alert(data.extend.msg);
                    }else{
                        //待解决：在对应位置显示错误信息
                    }
                    if(data.code==0){
                        return false;
                    }else{
                        window.location.href="/admin/article";
                    }
                }
            })
            return false;
        })
        
        //刷新实现重置
        $("#init").click(function () {
            window.location.reload();
        })

        /*
        //不刷新页面实现重置
        $("#init").click(function () {
            //Note：$两边需要加""
            $("#articleTitle").val("<%--${article.articleTitle}--%>");
            $("#articleContent").val();
            //Note:这样改变不会触发$("#parent-category-select").change()。
            $("#parent-category-select").val("<%--${article.categoryList[0].categoryId}--%>");
            $("#tagList > input").each(function () {
                <%--<c:forEach var="at" items="${article.tagList}">--%>
                    //Note:操作checked、disabled、selected属性，建议只用prop()方法，不要使用attr()方法。
                    if($(this).val()==<%--${at.tagId}--%>){
                        $(this).prop("checked", true);
                    }else{
                        $(this).prop("checked", false);
                    }
                <%--</c:forEach>--%>
            });
            $("#status > input").each(function () {
                if($(this).val()==<%--${article.articleStatus}--%>){
                    $(this).prop("checked", true);
                }else{
                    $(this).prop("checked", false);
                }
            });
        })*/
    </script>
</rapid:override>





<%@ include file="/WEB-INF/viewJsp/admin/adminFramework.jsp" %>
