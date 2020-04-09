<%@ page import="dal.model.Admin" %>
<%@ page import="dal.model.RequestList" %>
<%--
  Created by IntelliJ IDEA.
  User: addzero
  Date: 2020/3/14
  Time: 16:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%!
    Admin thisAdmin = null;
%>
<html>
<head>
    <title>客户响应及派工管理系统-客户请求</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bmap.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="https://api.map.baidu.com/api?v=1.0&&type=webgl&ak=Lm1k2R6SybtdXGL0bFhbdQM8rG6DQDvs"></script>
    <script src="js/main.js"></script>
</head>
<body>
<%
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
                    <a class="navbar-brand" href="main.jsp">管理系统</a>
                </div>

                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li>
                            <a href="main.jsp">主页</a>
                        </li>
                        <li class="active">
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
        <div class="col-md-8 column">
            <button type="button" class="btn btn-block" data-toggle="collapse" data-target="#map_canvas">折叠地图</button>
            <div class="collapse in" id="map_canvas"></div>
        </div>
        <div class="col-md-4 column">
            <div id="request_info">
                <table class="table table-bordered" id="info_table">
                    <thead>
                    <tr>
                        <th>属性</th>
                        <th>值</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <th>客户ID</th>
                        <th></th>
                    </tr>
                    <tr>
                        <th>请求时间</th>
                        <th></th>
                    </tr>
                    <tr>
                        <th>请求状态</th>
                        <th></th>
                    </tr>
                    <tr>
                        <th>请求地点</th>
                        <th></th>
                    </tr>
                    </tbody>
                </table>
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
<script>
    var map = new BMapGL.Map("map_canvas");
    //地图中心设置为重庆大学
    var point = new BMapGL.Point(106.475, 29.571);
    map.centerAndZoom(point, 15);
    map.enableScrollWheelZoom(true);
    setInterval(flashMap, 1000);
</script>
</body>
</html>
