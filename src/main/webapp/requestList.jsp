<%@ page import="dal.model.Admin" %>
<%@ page import="bll.controller.RequestController" %>
<%@ page import="bll.controller.RequestControllerImpl" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
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
        if(Pattern.matches("application/json; charset=(UTF|utf)-8", request.getContentType())) {
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder strRequest = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                strRequest.append(line);
            }
            JSONObject jsonRequest = new JSONObject(strRequest.toString());
            if ("flushMap".equals(jsonRequest.getString("request-type"))) {
                requestController.requestFlush(response, jsonRequest.getInt("trigger"));
            }
            else if ("reject".equals(jsonRequest.getString("request-type"))) {
                requestController.requestReject(response, jsonRequest.getString("rid"), jsonRequest.getString("reason"));
            }
        }
    } %>
</body>
</html>