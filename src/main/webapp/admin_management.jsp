<%@ page import="dal.model.Admin" %><%--
  Created by IntelliJ IDEA.
  User: addzero
  Date: 2020/3/16
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%!
    Admin thisAdmin = null;
%>
<html>
<head>
    <title>客户响应及派工管理系统-管理员操作</title>
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
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">管理员操作<strong class="caret"></strong></a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="#">功能开发中</a>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <a href="#">功能开发中</a>
                                </li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">其他操作<strong class="caret"></strong></a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="staff_management.jsp">客服管理</a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">账号管理<strong class="caret"></strong></a>
                            <ul class="dropdown-menu">
                                <li class="active">
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
        <div class="col-md-12 column">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h5 class="panel-title">
                        当前管理信息
                    </h5>
                </div>
                <div class="panel-body">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>属性</th>
                                <th>值</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <th>管理员总数</th>
                                <th></th>
                            </tr>
                            <tr>
                                <th>当前在线管理员总数</th>
                                <th></th>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="panel-footer">
                    <button class="btn btn-primary" data-toggle="modal" data-target="#search_admin">管理员查询</button>
                    <button class="btn btn-default" data-toggle="modal" data-target="#add_admin">新增管理员</button>
                    <button class="btn btn-danger" onclick="getSystemAdminsInfo()">刷新</button>
                </div>
            </div>
        </div>
    </div>
    <div class="row clearfix" id="admin_management">
        <div class="col-md-12 column">
            <div class="modal fade" id="add_admin" aria-labelledby="modal_label_1" aria-hidden="true" tabindex="-1" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="modal_label_1">新增管理员</h4>
                        </div>
                        <div class="modal-body">
                            <form class="form-horizontal" role="form">
                                <div class="form-group">
                                    <label for="add_aid" class="col-sm-2 control-label">账号</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="add_aid">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="add_aname" class="col-sm-2 control-label">姓名</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="add_aname">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="add_authority" class="col-sm-2 control-label">权限</label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" id="add_authority" readonly value="<%=thisAdmin.getAuthority() + 1%>">
                                    </div>
                                    <div class="col-sm-4">
                                        <button type="button" class="btn btn-default" onclick="plusAuthority()">
                                            <span class="glyphicon glyphicon-plus"></span>
                                        </button>
                                        <button type="button" class="btn btn-default" onclick="minusAuthority(<%=thisAdmin.getAuthority()%>)">
                                            <span class="glyphicon glyphicon-minus"></span>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary" onclick="addAdmin()">提交</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="search_admin" aria-labelledby="modal_label_2" aria-hidden="true" tabindex="-1" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="modal_label_2">查询管理员</h4>
                        </div>
                        <div class="modal-body">
                            <form class="form-horizontal" role="form">
                                <div class="form-group">
                                    <label for="search_aid" class="col-sm-2 control-label">按账号搜索</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="search_aid">
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary" onclick="searchAdmin()">查询</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="modify_admin" aria-labelledby="modal_label_3" aria-hidden="true" tabindex="-1" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="modal_label_3">修改管理员信息</h4>
                        </div>
                        <div class="modal-body">
                            <form class="form-horizontal" role="form">
                                <div class="form-group">
                                    <label for="modify_aid" class="col-sm-2 control-label">账号</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="modify_aid" readonly>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="modify_aname" class="col-sm-2 control-label">姓名</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="modify_aname">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="modify_telephone" class="col-sm-2 control-label">电话</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="modify_telephone">
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
                                    <label for="modify_authority" class="col-sm-2 control-label">权限</label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" id="modify_authority" readonly>
                                    </div>
                                    <div class="col-sm-4">
                                        <button type="button" class="btn btn-default" onclick="plusAuthority()">
                                            <span class="glyphicon glyphicon-plus"></span>
                                        </button>
                                        <button type="button" class="btn btn-default" onclick="minusAuthority(<%=thisAdmin.getAuthority()%>)">
                                            <span class="glyphicon glyphicon-minus"></span>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary" onclick="modifyAdmin()">提交</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="info_admin" aria-labelledby="modal_label_4" aria-hidden="true" tabindex="-1" role="table">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="modal_label_4">管理员信息</h4>
                        </div>
                        <div class="modal-body">
                            <table class="table table-bordered table-condensed" id="admin_table">
                                <thead>
                                <tr>
                                    <th class="text-center">属性</th>
                                    <th class="text-center">值</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <th class="text-center">管理员ID</th>
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
                                </tbody>
                            </table>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary" data-dismiss="modal" data-toggle="modal" data-target="#modify_admin">修改</button>
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
<script>
    $(function () {
        getSystemAdminsInfo();
    });

    $("#modify_admin").on("show.bs.modal", function () {
        $("#modify_aid").val($("#admin_table>tbody>tr:nth-child(1)>th:nth-child(2)").text());
        $("#modify_authority").val($("#admin_table>tbody>tr:nth-child(6)>th:nth-child(2)").text());
    });

    $("#add_admin").on("hidden.bs.modal", function () {
        $("#add_admin form input[type=text]").val("");
    });

    $("#search_admin").on("hidden.bs.modal", function () {
        $("#search_admin form input[type=text]").val("");
    });

    $("#modify_admin").on("hidden.bs.modal", function () {
        $("#modify_admin form input[type=text]").val("");
        $("#modify_admin form input[type=radio]").removeAttr("checked");
    });
</script>
</html>
