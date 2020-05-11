<%@ page import="dal.model.Admin" %><%--
  Created by IntelliJ IDEA.
  User: addzero
  Date: 2020/5/11
  Time: 19:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%!
    Admin thisAdmin = null;
%>
<html>
<head>
    <title>客户响应及派工管理系统-客户管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.staticfile.org/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css" rel="stylesheet">
    <link href="https://cdn.staticfile.org/bootstrap-table/1.16.0/bootstrap-table.min.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <script src="https://cdn.staticfile.org/bootstrap-table/1.16.0/bootstrap-table.min.js"></script>
    <script src="https://cdn.staticfile.org/bootstrap-table/1.16.0/locale/bootstrap-table-zh-CN.min.js"></script>
    <script src="js/alertBox.js"></script>
    <script src="js/main.js"></script>
    <script src="js/customer_management.js"></script>
</head>
<body>
<%
    response.addCookie(new Cookie("JSESSIONID", session.getId()));
    thisAdmin = (Admin) session.getAttribute("admin");
    if(thisAdmin == null) { %>
<script>window.alert("无效的管理员信息!")</script>
<%  } else { %>
<div class="container">
    <div class="row clearfix" id="navbar">
        <div class="col-md-12 column">
            <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">切换导航</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="MainPage">管理系统</a>
                </div>

                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li>
                            <a href="MainPage">主页</a>
                        </li>
                        <li>
                            <a href="RequestManage">请求派工</a>
                        </li>
                        <li>
                            <a href="StaffManage">客服管理</a>
                        </li>
                        <li>
                            <a href="AdminManage">管理员操作</a>
                        </li>
                        <li>
                            <a href="Stat">统计数据</a>
                        </li>
                        <li class="active">
                            <a href="CustomerManage">客户管理</a>
                        </li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">账号管理<strong class="caret"></strong></a>
                            <ul class="dropdown-menu">
                                <li class="active">
                                    <a href="MyInfo">账号信息</a>
                                </li>
                                <li class="divider" ></li>
                                <li>
                                    <a onclick="logout()" href="#">注销</a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>

            </nav>
        </div>
    </div>
    <div class="row clearfix" id="content">
        <div class="col-md-12 column">
            <div class="btn-group btn-group-justified">
                <div class="btn-group">
                    <button type="button" class="btn btn-default" data-toggle="collapse" data-target="#customers-panel">折叠</button>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn btn-primary" onclick="flashCustomers()">刷新</button>
                </div>
            </div>
            <div class="panel panel-info collapse in" id="customers-panel">
                <div class="panel-heading">
                    <h5 class="panel-title text-center">
                        所有客服
                    </h5>
                </div>
                <div class="panel-body">
                    <table class="table table-bordered" id="customers-table">
                        <thead>
                        <tr>
                            <th class="text-center" data-field="Cindex" data-sortable="true">序号</th>
                            <th class="text-center" data-field="Cname" data-sortable="true">客户</th>
                            <th class="text-center" data-field="Cid" data-sortable="true">OpenID</th>
                            <th class="text-center" data-field="Cgender" data-sortable="true">性别</th>
                            <th class="text-center" data-field="Cbirthday" data-sortable="true">生日</th>
                            <th class="text-center" data-field="Ctelephone" data-sortable="true">电话</th>
                            <th class="text-center" data-field="Cemail" data-sortable="true">邮箱</th>
                            <th class="text-center" data-field="CtotalRequestTimes" data-sortable="true">请求次数(总)</th>
                            <th class="text-center" data-field="CtotalDispatchTimes" data-sortable="true">派工次数(总)</th>
                        </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </div>
                <div class="panel-footer">
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
