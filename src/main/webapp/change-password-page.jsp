<%--
  Created by IntelliJ IDEA.
  User: Davit
  Date: 14.12.2023
  Time: 16:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null){
        response.getWriter().println(errorMessage);
    }
%>
<head>
    <title>Change Password</title>
</head>
<body>
<form method="post" action="/change-password">
    <h1>Change Password</h1><br><br>
    Old password:<input type="text" name="oldPassword"><br><br>
    New password:<input type="text" name="newPassword"><br><br>
    Confirm password:<input type="text" name="confirmPassword"><br><br>
    <input type="submit" name="submit">
</form>

</body>
</html>
