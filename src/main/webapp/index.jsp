<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>CRDMsystem</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.6/js/bootstrap.min.js"></script>
</head>
<body>
<%
    if(session.getAttribute("admin") != null) session.removeAttribute("admin");
%>
<div class="container" >
    <div class="row clearfix" id="content">
        <div class="col-md-12 column">
            <h1 class="text-center">客户响应及派工管理系统</h1>
            <form role="form" action="main.jsp" method="post">
                <div class="form-group">
                    <label for="aid" class="control-label">账号</label>
                    <input type="text" class="form-control" id="aid" name="aid" placeholder="请输入管理员账号" />
                </div>
                <div class="form-group">
                    <label for="password" class="control-label">密码</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码" />
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-default">登录</button>
                </div>
            </form>
        </div>
    </div>
</div>
<footer class="docs-footer">
    <div class="container">
        备案号：<a href="http://beian.miit.gov.cn" target="_blank">渝ICP备20002126号-1</a>
    </div>
</footer>
</body>
</html>
