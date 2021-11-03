<%--
  Created by IntelliJ IDEA.
  User: 19228
  Date: 2021/10/8
  Time: 21:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册</title>
    <script type="text/javascript"
            src="/js/jquery-1.12.4.min.js"></script>
    <link
            href="/plugin/bootstrap-3.3.7-dist/css/bootstrap.min.css"
            rel="stylesheet">
    <script
            src="/plugin/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <style type="text/css">

    </style>

</head>
<body>
    <div class="row">
        <div class="col-md-2 col-md-offset-5">
            <form id="registerForm">
                <div class="form-group">
                    <label for="userName" class="control-label">用户名<br/></label>
                    <input id="userName" type="text" name="userName" class="form-control" placeholder="请输入用户名">
                    <span id="userName-validate-msg" class="help-block"></span>
                </div>
                <div class="form-group">
                    <label for="userNickname" class="control-label">昵称<br/></label>
                    <input id="userNickname" type="text" name="userNickname" class="form-control" placeholder="请输入昵称">
                    <span id="userNickName-validate-msg" class="help-block"></span>
                </div>
                <div class="form-group">
                    <label for="userEmail" class="control-label">电子邮箱<br/></label>
                    <input id="userEmail" type="text" name="userEmail" class="form-control" placeholder="请输入昵称">
                    <span id="userEmail-validate-msg" class="help-block"></span>
                </div>
                <div class="form-group">
                    <label for="userPass" class="control-label">登录密码<br/></label>
                    <input id="userPass" type="password" name="userPass" class="form-control" placeholder="请输入登录密码">
                    <span id="userPassword-validate-msg" class="help-block"></span>
                </div>
                <div class="form-group">
                    <label for="confirmUserPass" class="control-label">确认密码<br/></label>
                    <input id="confirmUserPass" type="password" class="form-control" name="confirmUserPass" placeholder="请再次确认密码">
                    <span id="confirmPassword-validate-msg" class="help-block"></span>
                </div>
                <div>
                    <input type="button" id="submit-btn" class="btn btn-default" value="注册">
                </div>
            </form>
        </div>
    </div>

    <script type="text/javascript">
        $(function () {
            /*Note:jQuery 对象是 dom 对象的数组 + jQuery 提供的一系列功能函数，如下的removeClass是对每一个数组元素进行*/
            /*每次进入页面首先清除表单中的校验样式和提示信息*/
            $("#registerForm").find("*").removeClass("has-error has-success");
            $("#registerForm").find(".help-block").text("");
        });

        /*工具方法，添加校验状态到表单input中*/
        function show_validate_msg(ele,status,msg){
            $(ele).parent().removeClass("has-error has-success");
            $(ele + " + span").text("");
            $(ele).parent().addClass(status);
            $(ele + " + span").text(msg);
        }
        /*Note:this是一个DOM对象，而$(this)是一个jQery对象*/
        <%--用户名前端校验：用户名一栏发生变化就发送请求到后端进行校验--%>
        $("#userName").change(function () {
            $.ajax({
                url: "/checkUserName",
                type: "GET",
                data: "userName="+this.value,
                success:function (data) {
                    if(data.code==0){
                        show_validate_msg("#userName","has-error",data.extend.unMsg);
                    }else{
                        show_validate_msg("#userName","has-success",data.extend.unMsg);
                    }
                }
            });
        });
        /*昵称变化进行校验*/
        $("#userNickname").change(function () {
            var regPassword = /^[\u2E80-\u9FFF]{2,5}$/;
            if(!regPassword.test(this.value)){
                show_validate_msg("#userNickname","has-error","昵称必须是2-5位的中文");
            }else{
                show_validate_msg("#userNickname","has-success","昵称符合格式要求");
            }
            return false;
        });
        <%--邮箱前端校验：邮箱一栏发生变化就发送请求到后端进行校验--%>
        $("#userEmail").change(function () {
            $.ajax({
                url: "/checkUserEmail",
                type: "GET",
                data: "userEmail="+this.value,
                success:function (data) {
                    if(data.code==0){
                        show_validate_msg("#userEmail","has-error",data.extend.ueMsg);
                    }else{
                        show_validate_msg("#userEmail","has-success",data.extend.ueMsg);
                    }
                }
            });
            return false;
        });
        /*密码变化进行校验*/
        $("#userPass").change(function () {
            var regPassword = /^[a-zA-Z0-9_-]{6,18}$/;
            if(!regPassword.test(this.value)){
                show_validate_msg("#userPass","has-error","密码必须是6-18位的英文或者数字或者_-或其组合");
            }else{
                show_validate_msg("#userPass","has-success","密码符合格式要求");
            }
            if(this.value != $("#confirmUserPass").val()){
                show_validate_msg("#confirmUserPass","has-error","密码不一致");
            }else{
                show_validate_msg("#confirmUserPass","has-success","");
            }
            return false;
        });
        <%--确认密码处一旦发生变化就判断是否相等--%>
        $("#confirmUserPass").change(function () {
            if(this.value != $("#userPass").val()){
                show_validate_msg("#confirmUserPass","has-error","密码不一致");
            }else{
                show_validate_msg("#confirmUserPass","has-success","");
            }
            return false;
        });
        <%--点击注册按钮提交--%>
        $("#submit-btn").click(function () {
            $("#registerForm").find("*").removeClass("has-error has-success");
            $("#registerForm").find(".help-block").text("");
           $.ajax({
               url: "/registerVarify",
               type: "POST",
               data: $("#registerForm").serialize(),
               async: "false",
               dataType: "json",
               success:function (data) {
                   if(data.code==0){
                        if(data.extend.sysError!=undefined){
                            alert(data.extend.sysError);
                            return false;
                        }
                       for (const error in data.extend) {
                           show_validate_msg("#"+error,"has-error",data.extend[error]);
                       }
                   }else{
                       alert("注册成功");
                       window.location.href = "/login";
                   }
               }
           });
        });
    </script>

</body>
</html>
