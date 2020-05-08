<%@ page import="bll.controller.AdminController" %>
<%@ page import="bll.controller.AdminControllerImpl" %>
<%@ page import="dal.model.Admin" %>
<%@ page import="dal.model.ActiveAdminList" %>
<%@ page import="bll.service.DaoService" %>
<%@ page import="bll.service.DaoServiceImpl" %>
<%@ page import="dal.model.RequestList" %>
<%@ page import="java.util.Enumeration" %><%--
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
    DaoService daoService = new DaoServiceImpl();
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
    <script src="js/alertBox.js"></script>
    <script src="js/main.js"></script>
</head>
<body>
<%
    response.addCookie(new Cookie("JSESSIONID", session.getId()));
    boolean isReLogin = false;
    thisAdmin = (Admin) session.getAttribute("admin");
    if(thisAdmin == null) {
        thisAdmin = adminController.adminLogin(request.getParameter("aid"), request.getParameter("password"));
        if(thisAdmin != null) {
            session.setAttribute("admin", thisAdmin);
            ActiveAdminList.getInstance().appendActiveAdmin(thisAdmin);
        } else { %>
<script>alertLoginNoUser()</script>
<%      }
    } else {
        Enumeration<String> paramNames = request.getParameterNames();
        while(paramNames.hasMoreElements()) {
            if("aid".equals(paramNames.nextElement()))
            {
                isReLogin = true;
                break;
            }
        }
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
            <a class="navbar-brand" href="MainPage">管理系统</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="active">
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
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">账号管理<strong class="caret"></strong></a>
                    <ul class="dropdown-menu">
                        <li>
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
    <div class="row clearfix" id="content">
        <div class="col-md-12 column">
            <h3>
                管理员：<% out.print(thisAdmin.getName()); %>，你好！
            </h3>
            <div class="row clearfix">
                <div class="col-md-6 column">
                    <div id="map_canvas"></div>
                </div>
                <div class="col-md-2 column">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h5 class="panel-title text-center">
                                管理员总览
                            </h5>
                        </div>
                        <div class="panel-body">
                            <table class="table table-bordered" id="admin_overview">
                                <thead>
                                <tr>
                                    <th class="text-center">属性</th>
                                    <th class="text-center">值</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <th class="text-center">管理员总数</th>
                                    <th class="text-center"><%out.print(daoService.getDao().getAllAdmins().size());%></th>
                                </tr>
                                <tr>
                                    <th class="text-center">当前在线管理员总数</th>
                                    <th class="text-center"><%out.print(ActiveAdminList.getInstance().getLength());%></th>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="panel-footer">
                            <button class="btn-primary btn" onclick="window.location.href='AdminManage'">进入管理员界面</button>
                        </div>
                    </div>
                </div>
                <div class="col-md-2 column">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h5 class="panel-title text-center">
                                客服总览
                            </h5>
                        </div>
                        <div class="panel-body">
                            <table class="table table-bordered" id="staff_overview">
                                <thead>
                                <tr>
                                    <th class="text-center">属性</th>
                                    <th class="text-center">值</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <th class="text-center">客服总数</th>
                                    <th class="text-center"><%out.print(daoService.getDao().getAllStaffs().size());%></th>
                                </tr>
                                <tr>
                                    <th class="text-center">空闲客服数</th>
                                    <th class="text-center"><%out.print(daoService.getDao().getStaffsByStatus("idle").size());%></th>
                                </tr>
                                <tr>
                                    <th class="text-center">任务中客服数</th>
                                    <th class="text-center"><%out.print(daoService.getDao().getStaffsByStatus("onduty").size());%></th>
                                </tr>
                                <tr>
                                    <th class="text-center">未知状态</th>
                                    <th class="text-center"><%out.print(daoService.getDao().getAllStaffs().size() -
                                            daoService.getDao().getStaffsByStatus("onduty").size() -
                                            daoService.getDao().getStaffsByStatus("idle").size());%></th>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="panel-footer">
                            <button class="btn-primary btn" onclick="window.location.href='StaffManage'">进入客服管理界面</button>
                        </div>
                    </div>
                </div>
                <div class="col-md-2 column">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h5 class="panel-title text-center">
                                请求总览
                            </h5>
                        </div>
                        <div class="panel-body">
                            <table class="table table-bordered" id="requests_overview">
                                <thead>
                                <tr>
                                    <th class="text-center">属性</th>
                                    <th class="text-center">值</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <th class="text-center">当前请求总数</th>
                                    <th class="text-center"><%out.print(RequestList.getInstance().getLength());%></th>
                                </tr>
                                <tr>
                                    <th class="text-center">未派工请求数</th>
                                    <th class="text-center"><%out.print(RequestList.getInstance().getWaitingRequests());%></th>
                                </tr>
                                <tr>
                                    <th class="text-center">已派工请求数</th>
                                    <th class="text-center"><%out.print(RequestList.getInstance().getLength() -
                                            RequestList.getInstance().getWaitingRequests());%></th>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="panel-footer">
                            <button class="btn-primary btn" onclick="window.location.href='RequestManage'">进入请求处理界面</button>
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
<%
    if(isReLogin) {
%>
<script>alertUserLoggedin()</script>
<%
    }
%>
</body>
</html>
