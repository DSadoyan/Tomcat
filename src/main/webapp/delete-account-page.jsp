
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null){
        response.getWriter().println(errorMessage);
    }
%>
<head>
    <title>Delete Account</title>
</head>
<body>
<form method="post" action="/delete-account" >
    <H1>Delete Account</H1><br><br>
    password:<input type="text" name="password"><br><br>
    <input type="submit" name="submit">
</form>

</body>
</html>
