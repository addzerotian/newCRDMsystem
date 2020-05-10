<%@ page import="dal.model.Admin" %>
<%@ page import="dal.model.Request" %>
<%@ page import="dal.model.RequestList" %><%--
  Created by IntelliJ IDEA.
  User: addzero
  Date: 2020/4/22
  Time: 20:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%!
    Admin thisAdmin = null;
%>
<html>
<head>
    <title>客户响应及派工管理系统-派工</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bmap.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="https://api.map.baidu.com/api?v=3.0&ak=Lm1k2R6SybtdXGL0bFhbdQM8rG6DQDvs"></script>
    <script src="js/alertBox.js"></script>
    <script src="js/main.js"></script>
    <script src="js/dispatch.js"></script>
</head>
<body>
<%
    response.addCookie(new Cookie("JSESSIONID", session.getId()));
    thisAdmin = (Admin) session.getAttribute("admin");
    if(thisAdmin == null) { %>
<script>window.alert("无效的管理员信息!")</script>
<%  } else {
        String rid = request.getParameter("rid");
        if(rid == null) {
%>
<script>window.alert("错误的请求单号!")</script>
<%      } else {
            Request customerRequest = RequestList.getInstance().getRequest(rid);

%>
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
                        <li class="active">
                            <a href="RequestManage">客户请求</a>
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
        <div class="col-md-4 column">
            <div class="panel panel-info" id="staff_info">
                <div class="panel-heading" id="staff_info_heading">
                    <h5 class="panel-title text-center">
                        客服列表
                    </h5>
                </div>
                <div class="panel-body" id="staff_info_body">
                    <ul class="list-group"></ul>
                </div>
                <div class="panel-footer" id="staff_info_footer">
                </div>
            </div>
        </div>
        <div class="col-md-8 column">
            <button type="button" class="btn btn-block" data-toggle="collapse" data-target="#map_canvas">折叠地图</button>
            <div class="collapse in" id="map_canvas"></div>
        </div>
    </div>
    <div class="row clearfix" id="dispatch-modal">
        <div class="col-md-12 column">
            <div class="modal fade" id="staff-modal" aria-labelledby="modal_label_1" aria-hidden="true" tabindex="-1" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="modal_label_1">客服信息</h4>
                        </div>
                        <div class="modal-body">
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
                                    <th class="text-center">邮箱</th>
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
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary" onclick="dispatch()">派工</button>
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
<script>
    $(function () {
        getStaffsAroundCustomer(<%=customerRequest.getLongitude()%>, <%=customerRequest.getLatitude()%>);
    });
</script>
<%      }
    }%>
</body>
</html>
