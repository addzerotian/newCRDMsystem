<%@ page import="dal.model.Admin" %>
<%@ page import="bll.controller.AdminController" %>
<%@ page import="bll.controller.AdminControllerImpl" %><%--
  Created by IntelliJ IDEA.
  User: addzero
  Date: 2020/4/13
  Time: 17:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%!
    Admin thisAdmin = null;
%>
<html>
<head>
    <title>客户响应及派工管理系统-个人信息</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.staticfile.org/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <script src="https://cdn.staticfile.org/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
    <script src="https://cdn.staticfile.org/bootstrap-datepicker/1.9.0/locales/bootstrap-datepicker.zh-CN.min.js"></script>
    <script src="js/alertBox.js"></script>
    <script src="js/main.js"></script>
    <script src="js/myinfo.js"></script>
</head>
<body>
<%
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
                            <a href="RequestManage">客户请求</a>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">其他操作<strong class="caret"></strong></a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="StaffManage">客服管理</a>
                                </li>
                                <li class="divider" ></li>
                                <li>
                                    <a href="AdminManage">管理员操作</a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown active">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">个人信息<strong class="caret"></strong></a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="#modify_info" data-toggle="modal">修改信息</a>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <a href="#" onclick="deleteAdmin()">删除账号</a>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a onclick="logout()" href="#">注销</a>
                        </li>
                    </ul>
                </div>

            </nav>
        </div>
    </div>
    <div class="row clearfix" id="content">
        <div class="col-md-12 column">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h5 class="panel-title">
                        个人信息
                        <span><img class="avatar" id="my_avatar" alt="" src=""></span>
                    </h5>
                </div>
                <div class="panel-body">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th class="text-center">属性</th>
                            <th class="text-center">值</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <th class="text-center">ID</th>
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
                            <th class="text-center">权限等级</th>
                            <th class="text-center"></th>
                        </tr>
                        <tr>
                            <th class="text-center">邮箱</th>
                            <th class="text-center"></th>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="panel-footer">
                    <button class="btn btn-primary" data-toggle="modal" data-target="#modify_info">修改</button>
                    <button class="btn btn-default" onclick="flushInfo()">刷新</button>
                    <button class="btn btn-danger" onclick="deleteAdmin()">删除账号</button>
                </div>
            </div>
        </div>
    </div>
    <div class="row clearfix" id="management">
        <div class="row col-md-12">
            <div class="modal fade" id="modify_info" aria-labelledby="modal_label_1" aria-hidden="true" tabindex="-1" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="modal_label_1">修改信息</h4>
                        </div>
                        <div class="modal-body">
                            <form class="form-horizontal" role="form">
                                <div class="form-group">
                                    <label for="modify_aid" class="col-sm-2 control-label">账号</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="modify_aid">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="modify_aname" class="col-sm-2 control-label">姓名</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="modify_aname">
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
                                    <label for="modify_email" class="col-sm-2 control-label">邮箱</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="modify_email">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="modify_telephone" class="col-sm-2 control-label">电话</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="modify_telephone">
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
                            <button type="button" class="btn btn-primary" onclick="updateInfo()">提交</button>
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
