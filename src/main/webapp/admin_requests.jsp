<%@ page import="dal.model.Admin" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="bll.controller.AdminController" %>
<%@ page import="bll.controller.AdminControllerImpl" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="bll.service.FileRequestService" %>
<%@ page import="bll.service.FileRequestServiceImpl" %>
<%@ page import="java.util.Map" %>
<%--
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
    FileRequestService fileRequestService = new FileRequestServiceImpl();
%>
<html>
<head>
    <title>Admin Request</title>
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
        while((line = br.readLine()) != null) {
            strRequest.append(line);
        }
        JSONObject jsonRequest = new JSONObject(strRequest.toString());
        if ("getInfo".equals(jsonRequest.getString("request-type"))) {
            adminController.getInfo(response);
        } else if("searchAdmin".equals(jsonRequest.getString("request-type"))) {
            adminController.searchAdmin(response, jsonRequest.getString("aid"), Integer.parseInt(jsonRequest.getString("requestAdminAuth")));
        } else if("logout".equals(jsonRequest.getString("request-type"))) {
            adminController.adminLogout(response, thisAdmin);
            if(session.getAttribute("admin") != null) session.removeAttribute("admin");
        } else if("flushInfo".equals(jsonRequest.getString("request-type"))) {
            adminController.flushInfo(response, thisAdmin);
        } else if("deleteAdmin".equals(jsonRequest.getString("request-type"))) {
            adminController.deleteAdmin(response, thisAdmin);
            if(session.getAttribute("admin") != null) session.removeAttribute("admin");
        }

    }
    else if(Pattern.matches("multipart/form-data.*", request.getContentType())) {
        Map<String, Object> map = fileRequestService.parseRequest(request);
        if("addAdmin".equals(map.get("request-type"))) adminController.addAdmin(map, response);
        else if("modifyAdmin".equals(map.get("request-type"))) adminController.modifyAdmin(response, map);
        else if("updateInfo".equals(map.get("request-type"))) adminController.updateInfo(response, session, map);
        else if("test".equals(map.get("request-type"))){
            JSONObject jsonObject = new JSONObject();
            jsonObject.append("status", 0);
            fileRequestService.setResponse(response, jsonObject);
        }
    }
} %>
</body>
</html>
