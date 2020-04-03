<%@ page import="bll.controller.AdminController" %>
<%@ page import="bll.controller.AdminControllerImpl" %>
<%@ page import="dal.model.Admin" %><%--
  Created by IntelliJ IDEA.
  User: addzero
  Date: 2020/3/14
  Time: 16:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%!
    AdminController adminController = new AdminControllerImpl();
    Admin thisAdmin = null;
%>
<html>
<head>
    <title>客户响应及派工管理系统-主页</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <script src="js/main.js"></script>
</head>
<body>
<%
    thisAdmin = (Admin) session.getAttribute("admin");
    if(thisAdmin == null) {
        thisAdmin = adminController.adminLogin(request.getParameter("aid"), request.getParameter("password"));
        if(thisAdmin != null) {
            session.setAttribute("admin", thisAdmin);
        } else { %>
<script>alertLoginNoUser()</script>
<%      }
}
    if(thisAdmin != null) { %>
<div class="container">
    <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">切换导航</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="main.jsp">管理系统</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="active">
                    <a href="main.jsp">主页</a>
                </li>
                <li>
                    <a href="requests.jsp">客户请求</a>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">其他操作<strong class="caret"></strong></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="staff_management.jsp">客服管理</a>
                        </li>
                        <li class="divider" ></li>
                        <li>
                            <a href="admin_management.jsp">管理员操作</a>
                        </li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">账号管理<strong class="caret"></strong></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="admin_management.jsp">账号信息</a>
                        </li>
                        <li class="divider" ></li>
                        <li>
                            <a onclick="logout()" href="index.jsp">注销</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <h3>
                管理员：<% out.print(thisAdmin.getName()); %>，你好！
            </h3>
            <div class="row clearfix">
                <div class="col-md-4 column">
                    <h4 class="text-center">
                        管理数据
                    </h4>
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h5 class="panel-title">
                                Panel title
                            </h5>
                        </div>
                        <div class="panel-body">
                            Panel content
                        </div>
                        <div class="panel-footer">
                            Panel footer
                        </div>
                    </div>
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h5 class="panel-title">
                                Panel title
                            </h5>
                        </div>
                        <div class="panel-body">
                            Panel content
                        </div>
                        <div class="panel-footer">
                            Panel footer
                        </div>
                    </div>
                </div>
                <div class="col-md-4 column">
                    <h4 class="text-center">
                        客服数据
                    </h4>
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h5 class="panel-title">
                                Panel title
                            </h5>
                        </div>
                        <div class="panel-body">
                            Panel content
                        </div>
                        <div class="panel-footer">
                            Panel footer
                        </div>
                    </div>
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h5 class="panel-title">
                                Panel title
                            </h5>
                        </div>
                        <div class="panel-body">
                            Panel content
                        </div>
                        <div class="panel-footer">
                            Panel footer
                        </div>
                    </div>
                </div>
                <div class="col-md-4 column">
                    <h4 class="text-center">
                        客户请求数据
                    </h4>
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h5 class="panel-title">
                                Panel title
                            </h5>
                        </div>
                        <div class="panel-body">
                            Panel content
                        </div>
                        <div class="panel-footer">
                            Panel footer
                        </div>
                    </div>
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h5 class="panel-title">
                                Panel title
                            </h5>
                        </div>
                        <div class="panel-body">
                            Panel content
                        </div>
                        <div class="panel-footer">
                            Panel footer
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<footer class="docs-footer">
    <div class="container">
        备案号：<a href="http://beian.miit.gov.cn" target="_blank">渝ICP备20002126号-1</a>
    </div>
</footer>
<%  } %>
</body>
</html>
