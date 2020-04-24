<%@ page import="dal.model.Admin" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="bll.controller.DispatchController" %>
<%@ page import="bll.controller.DispatchControllerImpl" %><%--
  Created by IntelliJ IDEA.
  User: addzero
  Date: 2020/4/24
  Time: 17:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%!
    Admin thisAdmin = null;
    DispatchController dispatchController = new DispatchControllerImpl();
%>
<html>
<head>
    <title>Dispatch Request</title>
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
        if ("dispatch".equals(jsonRequest.getString("request-type"))) {
            dispatchController.doDispatch(response, jsonRequest.getString("rid"), jsonRequest.getString("sid"));
        }
    }
} %>
</body>
</html>
