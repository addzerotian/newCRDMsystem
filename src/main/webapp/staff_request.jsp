<%@ page import="bll.controller.StaffController" %>
<%@ page import="bll.controller.StaffControllerImpl" %>
<%@ page import="dal.model.Admin" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.io.IOException" %><%--
  Created by IntelliJ IDEA.
  User: addzero
  Date: 2020/4/3
  Time: 17:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%!
    Admin thisAdmin = null;
    StaffController staffController = new StaffControllerImpl();
%>
<html>
<head>
    <title>Staff Request</title>
</head>
<body>
<%
    thisAdmin = (Admin) session.getAttribute("admin");
    if(thisAdmin == null) { %>
<script>window.alert("无效的管理员信息!")</script>
<%  } else {
    BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
    String strRequest = "";
    String line = null;
    while((line = br.readLine()) != null) {
        strRequest += line;
    }
    JSONObject jsonRequest = new JSONObject(strRequest);
    if ("addStaff".equals(jsonRequest.getString("request-type"))) {
        staffController.addStaff(response, jsonRequest.getString("sid"));
    } else if("searchStaff".equals(jsonRequest.getString("request-type"))) {
        staffController.searchStaff(response, jsonRequest.getString("sid"));
    }
} %>
</body>
</html>
