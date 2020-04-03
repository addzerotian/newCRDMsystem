<%@ page import="dal.model.Admin" %>
<%@ page import="bll.controller.StaffController" %>
<%@ page import="bll.controller.StaffControllerImpl" %><%--
  Created by IntelliJ IDEA.
  User: addzero
  Date: 2020/3/16
  Time: 17:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%!
    Admin thisAdmin = null;
%>
<html>
<head>
    <title>客户响应及派工管理系统-客服管理</title>
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
                        <li>
                            <a href="requests.jsp">客户请求</a>
                        </li>
                        <li class="dropdown active">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">客服管理<strong class="caret"></strong></a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a data-toggle="modal" href="#add_staff">新增客服</a>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <a data-toggle="modal" href="#search_staff">查询客服</a>
                                </li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">其他操作<strong class="caret"></strong></a>
                            <ul class="dropdown-menu">
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
        </div>
    </div>
    <div class="row clearfix" id="content">
        <div class="col-md-8 column">
            <button type="button" class="btn btn-block" data-toggle="collapse" data-target="#map_canvas">折叠地图</button>
            <div class="collapse in" id="map_canvas"></div>
        </div>
        <div class="col-md-4 column">
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h5 class="panel-title text-center">
                        客服信息
                    </h5>
                </div>
                <div class="panel-body">
                </div>
                <table class="table table-bordered table-condensed" id="staff_table">
                    <thead>
                    <tr>
                        <th class="text-center">属性</th>
                        <th class="text-center">值</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <th class="text-center">客服ID</th>
                        <th class="text-center"></th>
                    </tr>
                    <tr>
                        <th class="text-center">姓名</th>
                        <th class="text-center"></th>
                    </tr>
                    <tr>
                        <th class="text-center">生日</th>
                        <th class="text-center"></th>
                    </tr>
                    <tr>
                        <th class="text-center">性别</th>
                        <th class="text-center"></th>
                    </tr>
                    <tr>
                        <th class="text-center">电话</th>
                        <th class="text-center"></th>
                    </tr>
                    <tr>
                        <th class="text-center">出勤次数(总)</th>
                        <th class="text-center"></th>
                    </tr>
                    <tr>
                        <th class="text-center">出勤时长(总)</th>
                        <th class="text-center"></th>
                    </tr>
                    <tr>
                        <th class="text-center">出勤评价(总)</th>
                        <th class="text-center"></th>
                    </tr>
                    <tr>
                        <th class="text-center">缺勤次数(总)</th>
                        <th class="text-center"></th>
                    </tr>
                    <tr>
                        <th class="text-center">出勤次数(月)</th>
                        <th class="text-center"></th>
                    </tr>
                    <tr>
                        <th class="text-center">出勤时长(月)</th>
                        <th class="text-center"></th>
                    </tr>
                    <tr>
                        <th class="text-center">出勤评价(月)</th>
                        <th class="text-center"></th>
                    </tr>
                    <tr>
                        <th class="text-center">缺勤次数(月)</th>
                        <th class="text-center"></th>
                    </tr>
                    </tbody>
                </table>
                <div class="panel-footer">
                </div>
            </div>
        </div>
    </div>
    <div class="row clearfix" id="staff_management">
        <div class="col-md-12 column">
            <div class="modal fade" id="add_staff" aria-labelledby="modal_label_1" aria-hidden="true" tabindex="-1" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="modal_label_1">新增客服</h4>
                        </div>
                        <div class="modal-body">
                            <form class="form-horizontal" role="form">
                                <div class="form-group">
                                    <label for="add_sid" class="col-sm-2 control-label">账号</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="add_sid">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="add_sname" class="col-sm-2 control-label">姓名</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="add_sname">
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary" onclick="addStaff()">提交</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="search_staff" aria-labelledby="modal_label_2" aria-hidden="true" tabindex="-1" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="modal_label_2">查询客服</h4>
                        </div>
                        <div class="modal-body">
                            <form class="form-horizontal" role="form">
                                <div class="form-group">
                                    <label for="search_sid" class="col-sm-2 control-label">按账号搜索</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="search_sid">
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary" onclick="searchStaff()">查询</button>
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
<script>
    var map = new BMapGL.Map("map_canvas");
    //地图中心设置为重庆大学
    var point = new BMapGL.Point(106.475, 29.571);
    map.centerAndZoom(point, 15);
    map.enableScrollWheelZoom(true);
</script>
</body>
</html>
