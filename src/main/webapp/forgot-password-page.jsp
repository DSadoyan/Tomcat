
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Forgot Password</title>
</head>
<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null){
        response.getWriter().println(errorMessage);
    }
%>
<body>
<form method="get" action="/forgot-password">
    <H1>Forgot Password</H1><br><br>
    email:<input type="text" name="email"><br><br>
    <input type="submit" name="submit">
</form>
</body>
</html>
