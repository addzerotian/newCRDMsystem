<%@ page import="bll.controller.AdminController" %>
<%@ page import="bll.controller.AdminControllerImpl" %>
<%@ page import="dal.model.Admin" %><%--
  Created by IntelliJ IDEA.
  User: addzero
  Date: 2020/5/10
  Time: 19:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%!
    AdminController adminController = new AdminControllerImpl();
    Admin thisAdmin = null;
%>
<html>
<head>
    <title>客户响应及派工管理系统-统计</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
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
    <script src="js/statics.js"></script>
</head>
<body>
<%
    response.addCookie(new Cookie("JSESSIONID", session.getId()));
    thisAdmin = (Admin) session.getAttribute("admin");
    if(thisAdmin == null) { %>
<script>window.alert("无效的管理员信息!")</script>
<%  } else { %>
<div class="container">
    <div class="row clearfix">
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
                        <li class="active">
                            <a href="Stat">统计数据</a>
                        </li>
                        <li>
                            <a href="CustomerManage">客户管理</a>
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
        </div>
    </div>
    <div class="row clearfix" id="content">
        <div class="col-md-12 column">
            <div class="btn-group btn-group-justified">
                <div class="btn-group">
                    <button type="button" class="btn btn-default" data-toggle="collapse" data-target="#requests-panel">折叠请求</button>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn btn-primary" onclick="flashRequests()">刷新</button>
                </div>
            </div>
            <div class="panel panel-info collapse in" id="requests-panel">
                <div class="panel-heading">
                    <h5 class="panel-title text-center">
                        请求统计
                    </h5>
                </div>
                <div class="panel-body">
                    <table class="table table-bordered" id="requests-table">
                        <thead>
                        <tr>
                            <th class="text-center" data-field="Rindex" data-sortable="true">序号</th>
                            <th class="text-center" data-field="Rname" data-sortable="true">客户</th>
                            <th class="text-center" data-field="Rtime" data-sortable="true">申请时间</th>
                            <th class="text-center" data-field="Rlocation">地点</th>
                            <th class="text-center" data-field="Rstatus" data-sortable="true">状态</th>
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
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="btn-group btn-group-justified">
                <div class="btn-group">
                    <button type="button" class="btn btn-default" data-toggle="collapse" data-target="#dispatches-panel">折叠派工</button>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn btn-primary" onclick="flashDispatches()">刷新</button>
                </div>
            </div>
            <div class="panel panel-info collapse in" id="dispatches-panel">
                <div class="panel-heading">
                    <h5 class="panel-title text-center">
                        派工统计
                    </h5>
                </div>
                <div class="panel-body">
                    <table class="table table-bordered" id="dispatches-table">
                        <thead>
                        <tr>
                            <th class="text-center" data-field="Dindex" data-sortable="true">序号</th>
                            <th class="text-center" data-field="Dcname" data-sortable="true">客户</th>
                            <th class="text-center" data-field="Dsname" data-sortable="true">客服</th>
                            <th class="text-center" data-field="DstartTime" data-sortable="true">请求发起时间</th>
                            <th class="text-center" data-field="DdispatchTime" data-sortable="true">派工时间</th>
                            <th class="text-center" data-field="DendTime" data-sortable="true">结束时间</th>
                            <th class="text-center" data-field="DwaitMinuts">客户等待时间</th>
                            <th class="text-center" data-field="Dstatus">状态</th>
                            <th class="text-center" data-field="Dstar">评分</th>
                            <th class="text-center" data-field="Dcomment">评价</th>
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
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="btn-group btn-group-justified">
                <div class="btn-group">
                    <button type="button" class="btn btn-default" data-toggle="collapse" data-target="#staffs-panel">折叠客服</button>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn btn-primary" onclick="flashStaffs()">刷新</button>
                </div>
            </div>
            <div class="panel panel-info collapse in" id="staffs-panel">
                <div class="panel-heading">
                    <h5 class="panel-title text-center">
                        客服统计
                    </h5>
                </div>
                <div class="panel-body">
                    <table class="table table-bordered" id="staffs-table">
                        <thead>
                        <tr>
                            <th class="text-center" data-field="Sindex" data-sortable="true">序号</th>
                            <th class="text-center" data-field="Sid" data-sortable="true">ID</th>
                            <th class="text-center" data-field="Sname" data-sortable="true">姓名</th>
                            <th class="text-center" data-field="Sbirthday" data-sortable="true">生日</th>
                            <th class="text-center" data-field="Sgender" data-sortable="true">性别</th>
                            <th class="text-center" data-field="Stelephone" data-sortable="true">电话</th>
                            <th class="text-center" data-field="Semail">邮箱</th>
                            <th class="text-center" data-field="SdutyTotalTimes" data-sortable="true">出勤次数(总)</th>
                            <th class="text-center" data-field="SdutyTotalHours" data-sortable="true">出勤时长(总)</th>
                            <th class="text-center" data-field="SgradeTotal" data-sortable="true">评分(总)</th>
                            <th class="text-center" data-field="SdutyMonthTimes" data-sortable="true">出勤次数(月)</th>
                            <th class="text-center" data-field="SdutyMonthHours" data-sortable="true">出勤时长(月)</th>
                            <th class="text-center" data-field="SgradeMonth" data-sortable="true">评分(月)</th>
                            <th class="text-center" data-field="SabsenceTotal" data-sortable="true">缺勤次数(总)</th>
                            <th class="text-center" data-field="SabsenceMonth" data-sortable="true">缺勤次数(月)</th>
                            <th class="text-center" data-field="Sstatus" data-sortable="true">当前状态</th>
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
