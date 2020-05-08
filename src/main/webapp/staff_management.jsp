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
    <link href="https://cdn.staticfile.org/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css" rel="stylesheet">
    <link href="css/bmap.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="https://api.map.baidu.com/api?v=3.0&ak=Lm1k2R6SybtdXGL0bFhbdQM8rG6DQDvs"></script>
    <script src="https://cdn.staticfile.org/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
    <script src="https://cdn.staticfile.org/bootstrap-datepicker/1.9.0/locales/bootstrap-datepicker.zh-CN.min.js"></script>
    <script src="js/alertBox.js"></script>
    <script src="js/main.js"></script>
    <script src="js/staff_management.js"></script>
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
        </div>
    </div>
    <div class="row clearfix" id="content">
        <div class="col-md-4 column">
            <div class="panel panel-info" id="staff_info">
                <div class="panel-heading" id="staff_info_heading">
                    <h5 class="panel-title text-center">
                        客服信息
                    </h5>
                </div>
                <div class="panel-body" id="staff_info_body">
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
                    <tr>
                        <th class="text-center">当前状态</th>
                        <th class="text-center"></th>
                    </tr>
                    </tbody>
                </table>
                <div class="panel-footer" id="staff_info_footer">
                </div>
            </div>
        </div>
        <div class="col-md-8 column">
            <div class="btn-group btn-group-justified">
                <div class="btn-group">
                    <button type="button" class="btn btn-default" data-toggle="collapse" data-target="#map_canvas">折叠地图</button>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn btn-primary" onclick="flushStaffInfo()">刷新地图</button>
                </div>
            </div>
            <div class="collapse in" id="map_canvas"></div>
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
                                <div class="form-group">
                                    <label for="add_sex" class="col-sm-2 control-label">性别</label>
                                    <div id="add_sex">
                                        <label class="radio-inline"><input type="radio" name="radio_sex" id="add_sex_male" value="male">男</label>
                                        <label class="radio-inline"><input type="radio" name="radio_sex" id="add_sex_female" value="female">女</label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="add_email" class="col-sm-2 control-label">邮箱</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="add_email">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="add_telephone" class="col-sm-2 control-label">电话</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="add_telephone">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="add_birth" class="col-sm-2 control-label">生日</label>
                                    <div class="col-sm-10">
                                        <input type="text" placeholder="选择日期" id="add_birth">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="add_avatar" class="col-sm-2 control-label">头像</label>
                                    <div class="col-sm-10">
                                        <input type="file" class="form-control" id="add_avatar">
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
                                    <label for="search_sid" class="col-sm-4 control-label" style="text-align: center">按账号搜索</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" id="search_sid">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="search_sname" class="col-sm-4 control-label" style="text-align: center">按姓名搜索</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" id="search_sname">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="search_totalAbscence" class="col-sm-4 control-label" style="text-align: center">查询缺勤次数大于</label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" id="search_totalAbscence">
                                    </div>
                                    <label for="search_totalAbscence" class="col-sm-2 control-label" style="text-align: left">次</label>
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
            <div class="modal fade" id="modify_staff" aria-labelledby="modal_label_3" aria-hidden="true" tabindex="-1" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="modal_label_3">修改客服信息</h4>
                        </div>
                        <div class="modal-body">
                            <form class="form-horizontal" role="form">
                                <div class="form-group">
                                    <label for="modify_sid" class="col-sm-2 control-label">账号</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="modify_sid" readonly>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="modify_sname" class="col-sm-2 control-label">姓名</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="modify_sname">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="modify_telephone" class="col-sm-2 control-label">电话</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="modify_telephone">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="modify_email" class="col-sm-2 control-label">邮箱</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="modify_email">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="modify_sex" class="col-sm-2 control-label">性别</label>
                                    <div id="modify_sex">
                                        <label class="radio-inline"><input type="radio" name="radio_sex" id="modify_sex_male" value="male">男</label>
                                        <label class="radio-inline"><input type="radio" name="radio_sex" id="modify_sex_female" value="female">女</label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="modify_birth" class="col-sm-2 control-label">生日</label>
                                    <div class="col-sm-10">
                                        <input type="text" placeholder="选择日期" id="modify_birth">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="modify_avatar" class="col-sm-2 control-label">头像</label>
                                    <div class="col-sm-10">
                                        <input type="file" class="form-control" id="modify_avatar">
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary" onclick="modifyStaff()">提交</button>
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
