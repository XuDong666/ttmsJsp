<%--
  Created by IntelliJ IDEA.
  User: xudong
  Date: 17-12-17
  Time: 下午8:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Object user= session.getAttribute("user");
%>
<html>
<head>
    <title>Title</title>
    <script src="../lib/jquery-3.2.1.min.js"></script>

    <link rel="stylesheet" href="../lib/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/admin.css">
</head>
<body>
<div class="container">
    <div class="head row">
        <div class="col-md-3 left">
            Hello,<a href="#" class="button"><img src="../images/huser.png" alt="#">${user.getname}</a>
        </div>
        <div class="col-md-offset-7 col-md-2 right"></div>
    </div>
</div>
</body>
</html>
