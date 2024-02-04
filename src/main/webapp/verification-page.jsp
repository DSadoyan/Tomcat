<%--
  Created by IntelliJ IDEA.
  User: Davit
  Date: 07.12.2023
  Time: 22:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Verification Page</title>
</head>
<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null){
        response.getWriter().println(errorMessage);
    }
%>
<body>
<form method="post" action="/verification">
    <h1>Verification Page</h1><br><br>
    verification code : <input type="text" name="verifyCode"><br><br>
    <input type="submit" name="submit">
</form>
</body>
</html>
