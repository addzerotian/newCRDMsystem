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
        wxController.customerLogin(response, request.getParameter("code"));
    }
    else if("register".equals(request.getParameter("request-type"))) {
        wxController.customerRegister(response, request);
    }
    else if("request".equals(request.getParameter("request-type"))) {
        wxController.sendRequest(response, request);
    }
    else if("get-info".equals(request.getParameter("request-type"))) {
        wxController.customerInfo(response, request.getParameter("cid"));
    }
    else if("update-info".equals(request.getParameter("request-type"))) {
        wxController.updateInfo(response, request);
    }
    else if("get-request-info".equals(request.getParameter("request-type"))) {
        wxController.getRequestInfo(response, request.getParameter("rid"));
    }
    else if("get-customer-requests".equals(request.getParameter("request-type"))) {
        wxController.getCustomerRequests(response, request.getParameter("cid"));
    }
    else if("get-dispatch-info".equals(request.getParameter("request-type"))) {
        wxController.getDispatchInfo(response, request.getParameter("did"));
    }
%>
</body>
</html>
