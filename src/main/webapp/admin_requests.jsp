<%@ page import="dal.model.Admin" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="bll.controller.AdminController" %>
<%@ page import="bll.controller.AdminControllerImpl" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="java.nio.charset.StandardCharsets" %><%--
  Created by IntelliJ IDEA.
  User: addzero
  Date: 2020/4/7
  Time: 23:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%!
    Admin thisAdmin = null;
    AdminController adminController = new AdminControllerImpl();
%>
<html>
<head>
    <title>Staff Request</title>
</head>
<body>
<%
    thisAdmin = (Admin) session.getAttribute("admin");
    if(thisAdmin == null) { %>

<%  } else {
    if(Pattern.matches("application/json; charset=(UTF|utf)-8", request.getContentType())) {
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        String strRequest = "";
        String line = null;
        while((line = br.readLine()) != null) {
            strRequest += line;
        }
        JSONObject jsonRequest = new JSONObject(strRequest);
        if ("getInfo".equals(jsonRequest.getString("request-type"))) {
            adminController.getInfo(response);
        } else if("searchAdmin".equals(jsonRequest.getString("request-type"))) {
            adminController.searchAdmin(response, jsonRequest.getString("aid"));
        } else if("modifyAdmin".equals(jsonRequest.getString("request-type"))) {
            adminController.modifyAdmin(response, jsonRequest);
        } else if("addAdmin".equals(jsonRequest.getString("request-type"))) {
            adminController.addAdmin(jsonRequest, response);
        } else if("logout".equals(jsonRequest.getString("request-type"))) {
            adminController.adminLogout(response, thisAdmin);
            if(session.getAttribute("admin") != null) session.removeAttribute("admin");
        }
    }
} %>
</body>
</html>
