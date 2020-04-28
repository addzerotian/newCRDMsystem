<%@ page import="bll.controller.StaffController" %>
<%@ page import="bll.controller.StaffControllerImpl" %>
<%@ page import="dal.model.Admin" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="dal.model.Staff" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="java.util.Map" %>
<%@ page import="bll.service.FileRequestService" %>
<%@ page import="bll.service.FileRequestServiceImpl" %><%--
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
    FileRequestService fileRequestService = new FileRequestServiceImpl();
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
            StringBuilder strRequest = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                strRequest.append(line);
            }
            JSONObject jsonRequest = new JSONObject(strRequest.toString());
            if ("searchStaff".equals(jsonRequest.getString("request-type"))) {
                staffController.searchStaff(response, jsonRequest.getString("sid"));
            } else if ("searchAroundStaffs".equals(jsonRequest.getString("request-type"))) {
                staffController.simuAroundStaffs(response, jsonRequest.getDouble("longitude"), jsonRequest.getDouble("latitude"));
            } else if ("flushStaff".equals(jsonRequest.getString("request-type"))) {
                staffController.flushStaff(response);
            }
        } else if (Pattern.matches("multipart/form-data.*", request.getContentType())) {
            Map<String, Object> map = fileRequestService.parseRequest(request);
            if ("addStaff".equals(map.get("request-type"))) staffController.addStaff(response, map);
            else if ("modifyStaff".equals(map.get("request-type"))) staffController.modifyStaff(response, map);
        }
} %>
</body>
</html>
