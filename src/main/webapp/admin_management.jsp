<%@ page import="dal.model.Admin" %><%--
  Created by IntelliJ IDEA.
  User: addzero
  Date: 2020/3/16
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%!
    Admin thisAdmin = null;
%>
<html>
<head>
    <title>客户响应及派工管理系统-管理员操作</title>
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
    <script src="js/admin_management.js"></script>
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
                        <li class="dropdown active">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">管理员操作<strong class="caret"></strong></a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="#add_admin" data-toggle="modal">新增管理员</a>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <a href="#search_admin" data-toggle="modal">查询管理员</a>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a href="Stat">统计数据</a>
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
                                <th class="text-center">属性</th>
                                <th class="text-center">值</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <th class="text-center">管理员总数</th>
                                <th class="text-center"></th>
                            </tr>
                            <tr>
                                <th class="text-center">当前在线管理员总数</th>
                                <th class="text-center"></th>
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
                                    <label for="add_authority" class="col-sm-2 control-label">权限</label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" id="add_authority" readonly value="<%=thisAdmin.getAuthority() + 1%>">
                                    </div>
                                    <div class="col-sm-4">
                                        <button type="button" class="btn btn-default" onclick="plusAuthority($('#add_authority'))">
                                            <span class="glyphicon glyphicon-plus"></span>
                                        </button>
                                        <button type="button" class="btn btn-default" onclick="minusAuthority($('#add_authority'), <%=thisAdmin.getAuthority()%>)">
                                            <span class="glyphicon glyphicon-minus"></span>
                                        </button>
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
                            <button type="button" class="btn btn-primary" onclick="submitByAdmin(addAdmin, <%=thisAdmin.getAuthority()%>)">提交</button>
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
                            <button type="button" class="btn btn-primary" onclick="submitByAdmin(searchAdmin, <%=thisAdmin.getAuthority()%>)">查询</button>
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
                                    <label for="modify_authority" class="col-sm-2 control-label">权限</label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" id="modify_authority" readonly>
                                    </div>
                                    <div class="col-sm-4">
                                        <button type="button" class="btn btn-default" onclick="plusAuthority($('#modify_authority'))">
                                            <span class="glyphicon glyphicon-plus"></span>
                                        </button>
                                        <button type="button" class="btn btn-default" onclick="minusAuthority($('#modify_authority'), <%=thisAdmin.getAuthority()%>)">
                                            <span class="glyphicon glyphicon-minus"></span>
                                        </button>
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
                            <button type="button" class="btn btn-primary" onclick="submitByAdmin(modifyAdmin, <%=thisAdmin.getAuthority()%>)">提交</button>
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
                            <img class="avatar" id="admin_avatar" alt="" src="">
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
                                <tr>
                                    <th class="text-center">邮箱</th>
                                    <th class="text-center"></th>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary">修改</button>
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
    $("#add_admin").on("show.bs.modal", function () {
        $("#add_authority").val(<%=thisAdmin.getAuthority() + 1%>);
    });
</script>
</html>
