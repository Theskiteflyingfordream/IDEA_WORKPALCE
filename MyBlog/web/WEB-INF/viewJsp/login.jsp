<%--
  Created by IntelliJ IDEA.
  User: 19228
  Date: 2021/9/28
  Time: 21:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>登录</title>
    <script type="text/javascript" src="/js/jquery-1.12.4.min.js"></script>
    <link
            href="/plugin/bootstrap-3.3.7-dist/css/bootstrap.min.css"
            rel="stylesheet">
    <script
            src="/plugin/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>
    <%
        /*Note:用<\%定义的变量$%\>无法使用{}输出，见username与userpass*/
        /*遍历cookie，取出其中的username和userpass*/
        Cookie[] cookies = request.getCookies();
        String username = "";
        String userpass = "";
        if(cookies!=null){
            for (int i = 0; i < cookies.length; i++) {
                if("username".equals(cookies[i].getName())) {
                    username = cookies[i].getValue();
                }
                if("userpass".equals(cookies[i].getName())) {
                    userpass = cookies[i].getValue();
                }
            }
        }
    %>

    <div class="row">
        <div class="col-md-2 col-md-offset-5">
            <form id="login-information">
                <div class="form-group">
                    <label for="userName" class="control-label">用户名或邮箱<br/></label>
                    <input id="userName" type="text" name="userName" class="form-control" value="<%=username%>" placeholder="请输入用户名或邮箱">
                    <span id="userName-validate-msg" class="help-block"></span>
                </div>
                <div class="form-group">
                    <label for="userPass" class="control-label">登录密码<br/></label>
                    <input id="userPass" type="password" name="userPass" class="form-control" value="<%=userpass%>" placeholder="请输入登录密码">
                    <span id="userPassword-validate-msg" class="help-block"></span>
                </div>
                <div class="checkbox">
                    <label>
                        <input id="rememberme" type="checkbox" name="rememberme" value="1">记住密码
                    </label>
                </div>
                <input type="button" id="submit-btn" class="btn btn-default" value="登录">

            </form>
        </div>
    </div>

    <script type="text/javascript">

        $(function () {
            /*Note:jQuery 对象是 dom 对象的数组 + jQuery 提供的一系列功能函数，如下的removeClass是对每一个数组元素进行*/
            /*每次进入页面首先清除表单中的校验样式和提示信息*/
            $("#login-information").find("*").removeClass("has-error has-success");
            $("#login-information").find(".help-block").text("");
        });

        /*工具方法，添加校验状态到表单input中*/
        function show_validate_msg(ele,status,msg){
            $(ele).parent().removeClass("has-error has-success");
            $(ele + " + span").text("");
            $(ele).parent().addClass(status);
            $(ele + " + span").text(msg);
        }

        $("#userName").change(function () {
            var userName = $(this).val();
            var regName = /(^[a-z0-9_-]{3,16}$)/;
            var regEmail = /(^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$)/;
            if(userName.indexOf("@")==-1){
                if(!regName.test(userName)) {
                    show_validate_msg("#userName","has-error","用户名必须是3-16位英文或数字的组合");
                }else{
                    show_validate_msg("#userName","has-success","");
                }
                return false;
            }else if(!regEmail.test(userName)) {
                show_validate_msg("#userName","has-error","邮箱格式不正确");
                return false;
            }
            show_validate_msg("#userName","has-success","");
            return false;
        });

        $("#userPass").change(function () {
            var password = $("#userPass").val();
            var regPassword = /^[a-zA-Z0-9_-]{6,18}$/;
            if(!regPassword.test(password)){
                show_validate_msg("#userPass","has-error","密码必须是6-18位的英文或者数字或者_-或其组合");
                return false;
            }else{
                show_validate_msg("#userPass","has-success","");
            }
        });

        /*Note:如果使用$("#login-information").submit则会按照form标签的action与method发请求，默认action为当前页面*/
        $("#submit-btn").click(function () {
            $.ajax({
                type: "POST",
                url:  "/loginVerify",
                data: $("#login-information").serialize(),
                success: function (data) {
                    if(data.code==1){
                        window.location.href = "/admin"
                    }else{
                        /*Note:如果data.extend.errorFields为空，则undefined != data.extend.errorFields.userName判断报错*/
                        for (const error in data.extend) {
                            show_validate_msg("#"+error,"has-error",data.extend[error]);
                        }
                        return false;
                    }
                }
            });
            return false;
        });
    </script>
</body>
</html>
