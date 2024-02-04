
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
<form method="post" action="/forgot-password">
    <H1>Forgot Password</H1><br><br>
    new password:<input type="text" name="newPassword"><br><br>
    confirm password:<input type="text" name="confirmPassword"><br><br>
    <input type="submit" name="submit">
</form>

</body>
</html>
