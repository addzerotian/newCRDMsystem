<%@ page import="bll.controller.WxController" %>
<%@ page import="bll.controller.WxControllerImpl" %><%--
  Created by IntelliJ IDEA.
  User: addzero
  Date: 2020/3/14
  Time: 19:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Wx</title>
    <%! WxController wxController = new WxControllerImpl(); %>
</head>
<body>
<%
    if("login".equals(request.getParameter("request-type")))
    {
        String uid = request.getParameter("uid");
        String password = request.getParameter("password");
        wxController.customerLogin(response, uid, password);
    }
    else if("register".equals(request.getParameter("request-type"))) {
        String uid = request.getParameter("uid");
        String password = request.getParameter("password");
        wxController.customerRegister(response, uid, password);
    }
    else if("request".equals(request.getParameter("request-type"))) {
        wxController.sendRequest(request);
    }
%>
</body>
</html>
