<%--
  Created by IntelliJ IDEA.
  User: Davit
  Date: 14.12.2023
  Time: 18:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Address Page</title>
    <style>
        body {
            text-align: center;
        }
        input.submit {
            width: 150px;
            background-color: #FF8800;
            color: white;
            border-radius: 10px;
            padding: 10px;
            font-size: 18px;
            text-decoration: none;
        }
    </style>
</head>
<body>
<h1>Address Page</h1>
</br>
<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) {
        response.getWriter().println(errorMessage);
    }
%>
<hr>
</br>
<div style="width: 100%; display: flex; justify-content: center">
    <form style="display: inline-block; text-align: center; width: 400px" method="post" action="/add-address">
        <p style="display: flex; justify-content: space-around"><span style="width: 50px">country:</span> <input
                type="text" name="country"><br><br></p>
        <p style="display: flex; justify-content: space-around"><span style="width: 50px">city:</span> <input
                type="text" name="city"><br><br></p>
        <p style="display: flex; justify-content: space-around"><span style="width: 50px">street:</span> <input
                type="text" name="street"><br><br></p>
        <p style="display: flex; justify-content: space-around"><span style="width: 50px">home:</span> <input
                type="text" name="home"><br><br></p>
        <input type=submit class="submit" name="submit"><br><br>
    </form>
</div>
</body>
</html>
