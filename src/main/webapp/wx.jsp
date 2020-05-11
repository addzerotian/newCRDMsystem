<%@ page import="bll.controller.WxController" %>
<%@ page import="bll.controller.WxControllerImpl" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="org.json.JSONObject" %><%--
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
    if(Pattern.matches("application/json; charset=(UTF|utf)-8", request.getContentType())) {
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder strRequest = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            strRequest.append(line);
        }
        JSONObject jsonRequest = new JSONObject(strRequest.toString());
        if ("flushCustomers".equals(jsonRequest.getString("request-type"))) {
            wxController.getAllCustomers(response);
        }
    } else {
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
        else if("dispatch-feedback".equals(request.getParameter("request-type"))) {
            wxController.feedback(response, request.getParameter("did"), Integer.parseInt(request.getParameter("star")), request.getParameter("comment"));
        }
        else if("get-customer-dispatches".equals(request.getParameter("request-type"))) {
            wxController.getCustomerDispatches(response, request.getParameter("cid"));
        }
    }
%>
</body>
</html>
