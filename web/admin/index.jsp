<%--
  Created by IntelliJ IDEA.
  User: xudong
  Date: 17-12-17
  Time: 下午8:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="ttms.model.EmployeeModel" %>
<%@page import="ttms.model.UserModel" %>
<%@page import="java.util.ArrayList" %>
<%
    EmployeeModel employee = (EmployeeModel) session.getAttribute("employee");
    String emp_name = employee.getEmp_name();

%>
<html>
<head>
    <title>西安成人影院</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="http://apps.bdimg.com/libs/jqueryui/1.10.4/css/jquery-ui.min.css">
    <script src="http://apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js">    </script>
    <script src="http://apps.bdimg.com/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
    <script src="../js/sidebar.js"></script>
    <link rel="stylesheet" href="../lib/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/admin.css">
    <style>
        body {
            background: url("../images/bg.png");
        }
    </style>

</head>
<body>
<div class="container">


    <%--head--%>
    <div class="row head">
        <div class="col-md-3">
            <a href="#" class="button"><img src="../images/huser.png" alt="#"> </a>
            <span>Hello,</span>

            <a href="#"><%=emp_name%>
            </a>
        </div>

        <div class="col-md-offset-7 col-md-2">

        </div>
    </div>



    <%--buttom    --%>
    <div class="row ">


        <%--sidebar--%>
        <div class="col-md-3 siderbar">
            <ul>
                <li>
                    <a href="/EmployeeServlet?method=searchByPage" id="employeemanager"><img
                            src="../images/用户管理.png" alt="">员工管理</a>
                </li>
                <li>
                    <a href="/UserServlet?method=searchByPage" id="userManager"><img src="../images/用户.png" alt="">用户管理</a>
                </li>
                <li>
                    <a href="#"><img src="../images/演播厅.png" alt="">演出厅管理</a>
                </li>
                <li>
                    <a href="#" id="seat"><img src="../images/座位.png" alt="">座位管理</a>
                    <ul id="studiomenu">
                        <a href="#">
                            <li>一号演出厅</li>
                        </a>
                        <a href="#">
                            <li>二号演出厅</li>
                        </a>
                        <a href="#">
                            <li>三号演出厅</li>
                        </a>
                    </ul>
                </li>
            </ul>
        </div>

        <%--content --%>

        <div class="col-md-8">

            <%--员工管理--%>
            <table class="table table-hover userlist" style="display: none">
                <caption>员工管理</caption>
                <thead>
                <th>员工编号</th>
                <th>员工账户</th>
                <th>姓名</th>
                <th>电话</th>
                <th>住址</th>
                <th>邮箱</th>
                <th>操作</th>
                </thead>
                <tbody>
                <tr>
                    <%
                        int currentPage=1;  //当前页
                        int allCount=0;     //总记录数
                        int allPageCount=0; //总页数
                        EmployeeModel Employee=null;

                        if(request.getAttribute("allEmployee")!=null)
                        {
                            //获取Action返回的信息
                            currentPage=((Integer)request.getAttribute("currentPage")).intValue();
                            ArrayList<EmployeeModel> list=(ArrayList<EmployeeModel>)request.getAttribute("allEmployee");
                            allCount=((Integer)request.getAttribute("allCount")).intValue();
                            allPageCount=((Integer)request.getAttribute("allPageCount")).intValue();
                            if(list!=null && list.size()>0)
                            {
                                for(int i=0;i<list.size();i++)
                                {
                                    if(i%2==0)
                                        out.println("<tr class='success'>");
                                    else
                                        out.println("<tr class='active'>");
                    %>
                    <th><%=list.get(i).getEmp_id()%></th>
                    <th><%=list.get(i).getEmp_no()%></th>
                    <th><%=list.get(i).getEmp_name()%></th>
                    <th><%=list.get(i).getEmp_tel_num()%></th>
                    <th><%=list.get(i).getEmp_addr()%></th>
                    <th><%=list.get(i).getEmp_email()%></th>
                    <td><a href="EmployeeServlet?method=searvhById&emp_id=<%=list.get(i).getEmp_id()%>" title="修改"><img src="../images/edit.png" alt=""></a>&nbsp;&nbsp;
                        <a href="EmployeeServlet?method=delete&emp_id=<%=list.get(i).getEmp_id()%>&emp_name=${search_emp_name}&currentPage=${currentPage}" title="删除"><img src="../images/delete.png" alt=""></a>
                    </td>
                </tr>
                <%
                            }
                        }
                    }
                %>
                </tbody>
            </table>
            <ul class="pagination paginationE" style="display: none">
                <li><a href="EmployeeServlet?method=searchByPage&currentPage=1&emp_name=${search_emp_name}">首页</a></li>
                <li><a href="EmployeeServlet?method=searchByPage&currentPage=<%=(currentPage-1)<1?1:(currentPage-1)%>&emp_name=${search_emp_name}">上一页</a></li>
                <li><a href="#" id="currentPage"><%=currentPage%></a></li>
                <li><a href="EmployeeServlet?method=searchByPage&currentPage=<%=(currentPage+1)>allPageCount?allPageCount:(currentPage+1)%>&emp_name=${search_emp_name}">下一页</a></li>
                <li><a href="EmployeeServlet?method=searchByPage&currentPage=<%=allPageCount%>&emp_name=${search_emp_name}">末页</a></li>
            </ul>
                <form action="/EmployeeServlet?method=searchByName" method="post" id="searchForm" class="form-inline" style="display: none">
                    <div class="form-group">
                        <input type="text" name="emp_name_s" placeholder="输入员工姓名查询" class="form-control">
                        <button type="submit"  class="btn btn-info">查询</button>
                    </div>

                </form>
                <button class="btn btn-info" id="addEmployee">添加</button>
            
            <%--用户管理--%>
                <table class="table table-hover ulist" style="display: none">
                    <caption>用户管理</caption>
                    <thead>
                    <th>用户头像</th>
                    <th>用户账户</th>
                    <th>用户密码</th>
                    <th>用户类型</th>
                    <th>操作</th>
                    </thead>
                    <tbody>
                    <tr>
                        <%
                            int currentPageU=1;  //当前页
                            int allCountU=0;     //总记录数
                            int allPageCountU=0; //总页数
                            UserModel User=null;

                            if(request.getAttribute("allUser")!=null)
                            {
                                //获取Action返回的信息
                                currentPageU=((Integer)request.getAttribute("currentPage")).intValue();
                                ArrayList<UserModel> list=(ArrayList<UserModel>)request.getAttribute("allUser");
                                allCountU=((Integer)request.getAttribute("allCount")).intValue();
                                allPageCountU=((Integer)request.getAttribute("allPageCount")).intValue();
                                if(list!=null && list.size()>0)
                                {
                                    for(int i=0;i<list.size();i++)
                                    {
                                        if(i%2==0)
                                            out.println("<tr class='success'>");
                                        else
                                            out.println("<tr class='active'>");
                        %>
                        <th><img src="<%=list.get(i).getHead_path()%>" alt=""></th>
                        <th><%=list.get(i).getEmp_no()%></th>
                        <th><%=list.get(i).getEmp_pass()%></th>
                        <th><%=list.get(i).getType()%></th>
                        <td><a href="UserServlet?method=searchByno&emp_no=<%=list.get(i).getEmp_no()%>" title="修改"><img src="../images/edit.png" alt=""></a>&nbsp;&nbsp;
                            <a href="UserServlet?method=delete&emp_no=<%=list.get(i).getEmp_no()%>" title="删除"><img src="../images/delete.png" alt=""></a>
                        </td>
                    </tr>
                    <%
                                }
                            }
                        }
                    %>
                    </tbody>
                </table>
                <ul class="pagination paginationU" style="display: none">
                    <li><a href="UserServlet?method=searchByPage&currentPage=1">首页</a></li>
                    <li><a href="UserServlet?method=searchByPage&currentPage=<%=(currentPageU-1)<1?1:(currentPageU-1)%>">上一页</a></li>
                    <li><a href="#" id="currentPageU"><%=currentPageU%></a></li>
                    <li><a href="UserServlet?method=searchByPage&currentPage=<%=(currentPageU+1)>allPageCountU?allPageCountU:(currentPageU+1)%>">下一页</a></li>
                    <li><a href="UserServlet?method=searchByPage&currentPage=<%=allPageCountU%>">末页</a></li>
                </ul>
                <form action="/UserServlet?method=searchByNo" method="post" id="searchFormU" class="form-inline" style="display: none">
                    <div class="form-group">
                        <input type="text" name="emp_no_u" placeholder="输入用户账号查询" class="form-control">
                        <button type="submit"  class="btn btn-info">查询</button>
                    </div>

                </form>
                <button class="btn btn-info" id="addU" >添加</button>
        </div>
    </div>
</div>

<%--修改员工dialog--%>
<div id="updateEmployeeDialog" style="display: none">
    <fieldset>
        <form action="/EmployeeServlet?method=update" method="post" id="updateForm" accept-charset="UTF-8">
            <%
            EmployeeModel emp=null;
            emp= (EmployeeModel) request.getAttribute("employee");
            if(emp!=null){

            %>

            <div class="form-group">
                <label for="emp_id">员工编号:</label>
                <input type="text" id="emp_id" name="emp_id" value="<%=emp.getEmp_id()%>" class="form-control">
            </div>
            <div>
                <label for="emp_no" class="control-label">员工账号:</label>
                <input type="text" id="emp_no" name="emp_no" value="<%=emp.getEmp_no()%>" class="form-control">
            </div>
            <div class="form-group" >
                <label for="name" class="control-label">姓名:</label>
                <input type="text" id="name" name="emp_name" value="<%=emp.getEmp_name()%>" class="form-control">
            </div>
            <div class="form-group">
                <label for="emp_addr" class="control-label">住址:</label>
                <input type="text" id="emp_addr" name="emp_addr" value="<%=emp.getEmp_addr()%>" class="form-control">
            </div>
            <div class="form-group">
                <label for="emp_tel_num" class="control-label">电话:</label>
                <input type="text" id="emp_tel_num" name="emp_tel_num" value="<%=emp.getEmp_tel_num()%>" class="form-control">
            </div>
            <div class="form-group">
                <label for="emp_email" class="control-label">邮箱:</label>
                <input type="text" id="emp_email" name="emp_email" value="<%=emp.getEmp_email()%>" class="form-control">
            </div>
            <% }%>
        </form>
    </fieldset>
</div>

<%--添加员工dialog--%>

<div id="addEmployeeDialog" style="display: none">
    <fieldset>
        <form action="/EmployeeServlet?method=add" method="post" id="addForm" accept-charset="UTF-8">
            <div>
                <label for="emp_no_add" class="control-label">员工账号:</label>
                <input type="text" id="emp_no_add" name="emp_no_add" class="form-control">
            </div>
            <div class="form-group" >
                <label for="emp_name_add" class="control-label">姓名:</label>
                <input type="text" id="emp_name_add" name="emp_name_add" class="form-control">
            </div>
            <div class="form-group">
                <label for="emp_addr_add" class="control-label">住址:</label>
                <input type="text" id="emp_addr_add" name="emp_addr_add" class="form-control">
            </div>
            <div class="form-group">
                <label for="emp_tel_num_add" class="control-label">电话:</label>
                <input type="text" id="emp_tel_num_add" name="emp_tel_num_add" class="form-control">
            </div>
            <div class="form-group">
                <label for="emp_email_add" class="control-label">邮箱:</label>
                <input type="text" id="emp_email_add" name="emp_email_add" class="form-control">
            </div>
        </form>
    </fieldset>
</div>

<%--添加用户--%>
<div id="addUserDialog" style="display: none">
    <fieldset>
        <form action="/UserServlet?method=add" method="post" id="addFormU" accept-charset="UTF-8">
            <div>
                <label for="emp_choose" class="control-label">选择员工:</label>
                <input id="emp_choose" name="user_name_add" list="emp_list" class="form-control" required>
                <datalist id="emp_list">
                    <%
                        if(request.getSession().getAttribute("allEmployee")!=null){
                            ArrayList<EmployeeModel> list=(ArrayList<EmployeeModel>)request.getSession().getAttribute("allEmployee");
                            for (int i=0;i<list.size();i++){
                                %>
                    <option value="<%=list.get(i).getEmp_name()%>">

                    <%}
                        }

                    %>

                </datalist>

            </div>
            <div class="form-group" >
                <label for="user_pwd_add" class="control-label">密码:</label>
                <input type="text" id="user_pwd_add" name="user_pwd_add" class="form-control" value="" required>
            </div>
            <div class="form-group">
                <label for="common" class="control-label" value="0">普通用户:</label>
                <input type="radio" id="common" name="type" checked="checked">
                <label for="admin" class="control-label">管理员:</label>
                <input type="radio" id="admin" name="type" value="1">
            </div>

        </form>
    </fieldset>
</div>

<%--修改用户--%>
<div id="updateUserDialog" style="display: none">
    <fieldset>
        <form action="/UserServlet?method=update" method="post" id="UserForm" accept-charset="UTF-8">
            <%
                UserModel user=null;
                user= (UserModel) request.getAttribute("user");
                if(user!=null){

            %>

            <div class="form-group">
                <label for="user_no">用户账号:</label>
                <input type="text" id="user_no" name="user_no" value="<%=user.getEmp_no()%>" class="form-control">
            </div>
            <div>
                <label for="user_pwd" class="control-label">用户密码:</label>
                <input type="text" id="user_pwd" name="user_pwd" value="<%=user.getEmp_pass()%>" class="form-control">
            </div>
            <div class="form-group" >
                <label for="admin1" class="control-label">管理员:</label>
                <input type="radio" id="admin1" name="user_type" value="1"  class="form-control">
                <label for="common1" class="control-label">普通用户</label>
                <input type="radio" id="common1" name="user_type" value="0" checked="checked">
            </div>
            <% }%>
        </form>
    </fieldset>
</div>
<script>
    if(${requestScope.isEmployee}){

        $(".userlist").css('display','block');
        $(".paginationE").css('display','block');
        $("#searchForm").css('display','block');
        $("#addEmployee").css('display','block');
    }

</script>
<script>
    if(${requestScope.isUser}){

        $(".ulist").css('display','block');
        $(".paginationU").css('display','block');
        $("#searchFormU").css('display','block');
        $("#addUser").css('display',"block");

    }

</script>

<script>
    if(${requestScope.updateEmployeeStatus}){
        $('#updateEmployeeDialog').dialog({
            autoOpen : true,   // 是否自动弹出窗口
            modal : true,    // 设置为模态对话框
            resizable : true,
            width : 600,   //弹出框宽度
            height : 500,   //弹出框高度
            title : '修改员工信息',  //弹出框标题
            position : 'center top',  //窗口显示的位置
            buttons:{
                '确定':function(){
                    $('#updateForm').submit();
                    $(this).dialog("close");
                },
                '取消':function(){
                    $(this).dialog("close");
                    // $(this).dialog("destroy");
                }
            }
        });
    }

</script>

<script>
    if(${requestScope.updateUserStatus}){
        $('#updateUserDialog').dialog({
            autoOpen : true,   // 是否自动弹出窗口
            modal : true,    // 设置为模态对话框
            resizable : true,
            width : 600,   //弹出框宽度
            height : 500,   //弹出框高度
            title : '修改员工信息',  //弹出框标题
            position : 'center top',  //窗口显示的位置
            buttons:{
                '确定':function(){
                    $('#UserForm').submit();
                    $(this).dialog("close");
                },
                '取消':function(){
                    $(this).dialog("close");
                    // $(this).dialog("destroy");
                }
            }
        });
    }
</script>
<script>
    $('#addEmployee').click(function () {
        $('#addEmployeeDialog').dialog({
            autoOpen : true,   // 是否自动弹出窗口
            modal : true,    // 设置为模态对话框
            resizable : true,
            width : 600,   //弹出框宽度
            height : 500,   //弹出框高度
            title : '添加员工',  //弹出框标题
            position : 'center top',  //窗口显示的位置
            buttons:{
                '确定':function(){
                    $('#addForm').submit();
                    $(this).dialog("close");
                },
                '取消':function(){
                    $(this).dialog("close");
                }
            }
        });
    })

</script>
<script >
    $('#addUser').click(function () {
        $('#addUserDialog').dialog({
            autoOpen : true,   // 是否自动弹出窗口
            modal : true,    // 设置为模态对话框
            resizable : true,
            width : 600,   //弹出框宽度
            height : 500,   //弹出框高度
            title : '添加用户',  //弹出框标题
            position : 'center top',  //窗口显示的位置
            buttons:{
                '确定':function(){
                    $('#addForm').submit();
                    $(this).dialog("close");
                },
                '取消':function(){
                    $(this).dialog("close");
                }
            }
        });
    })
</script>
</body>
</html>
