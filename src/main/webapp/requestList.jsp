<%@ page import="dal.model.Admin" %>
<%@ page import="bll.controller.RequestController" %>
<%@ page import="bll.controller.RequestControllerImpl" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%--
  Created by IntelliJ IDEA.
  User: addzero
  Date: 2020/3/27
  Time: 20:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%!
    Admin thisAdmin = null;
    RequestController requestController = new RequestControllerImpl();
%>
<html>
<head>
    <title>RequestList</title>
</head>
<body>
<%
    thisAdmin = (Admin) session.getAttribute("admin");
    if(thisAdmin == null) { %>

<%  }else {
        if("application/json".equals(request.getContentType())) {
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String strRequest = "";
            String line = null;
            while ((line = br.readLine()) != null) {
                strRequest += line;
            }
            JSONObject jsonRequest = new JSONObject(strRequest);
            if ("flushMap".equals(jsonRequest.getString("request-type"))) {
                requestController.requestFlush(response, jsonRequest.getInt("trigger"));
            }
        }
    } %>
</body>
</html>