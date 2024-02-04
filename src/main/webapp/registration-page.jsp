
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null){
            response.getWriter().println(errorMessage);
        }
%>
<head>
    <title>Registration Page</title>
</head>
<body>
<form method="post" action="/registration">
    <h1>Registration Page</h1><br><br>
    name:<input type="text" name="name"><br><br>
    surname:<input type="text" name="surname"><br><br>
    year:<input type="text" name="year"><br><br>
    email:<input type="text" name="email"><br><br>
    password:<input type="text" name="password"><br><br>
    <input type="submit" name="submit">
</form>

</body>
</html>
