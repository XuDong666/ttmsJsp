<%--
  Created by IntelliJ IDEA.
  User: xudong
  Date: 17-12-15
  Time: 下午8:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String msg = (String) session.getAttribute("msg");
%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>成人影院</title>
    <link rel="stylesheet" type="text/css" href="css/zui.css" media="all">
    <link rel="stylesheet" type="text/css" href="css/login.css" media="all">
    <link href="css/animate.min.css" rel="stylesheet">
    <link href="css/font-awesome.min.css" rel="stylesheet">
    <style>
        body {
            background-image: url(images/4.jpg);
        }

        .msg {
            display: none;
            color: red;
        }
    </style>
</head>
<body>
<div id="main-box"></div>
<div id="main-content">
    <div class="login-body  animated fadeInLeft">
        <div class="login-main pr">
            <form action="/loginServlet" method="post" class="login-form" accept-charset="UTF-8">
                <h3> 管理中心 </h3>
                <h5 style="padding-bottom: 10px"> System Management Center </h5>
                <!-- 账号登陆 -->
                <div id="MobileBox" class="item-box">
                    <div class="input-group user-name"><span class="input-group-addon"><i class="icon-user"></i></span>
                        <input type="text" id="username" name="username" class="form-control" placeholder="用户名">
                    </div>
                    <div class="input-group password"><span class="input-group-addon"><i class="icon-lock"></i></span>
                        <input type="password" id="password" name="password" class="form-control" placeholder="密码">
                    </div>
                    <div class="login_btn_panel">
                        <button class=" btn btn-primary btn-block btn-lg" data-ajax="post" type="submit"
                                data-callback="success">登录
                        </button>
                        <div class="check-tips"></div>
                    </div>
                    <p class="msg" id="msg">密码错误
                    </p>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    const actInput = document.getElementById('username');
    const pwdInput = document.getElementById('password');

    const showWarnMessage = () => {
        if (!${empty msg}) {
            document.getElementById('msg').style.display = 'block';
        }
    };

    actInput.addEventListener("focus", () => {
        document.getElementById('msg').style.display = 'none';
        ${msg=""};
    }, false);

    pwdInput.addEventListener("focus", () => {
        document.getElementById('msg').style.display = 'none';
        ${msg=""};
    }, false);

    showWarnMessage();
</script>
</body>
</html>