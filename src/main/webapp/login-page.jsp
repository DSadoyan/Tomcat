
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>
</head>
<%
    String errorMessage =(String) request.getAttribute("errorMessage");
    if (errorMessage!=null){
        response.getWriter().println(errorMessage);
    }
%>
<body   style="background-image: linear-gradient(cornflowerblue,peru)">
<form method="get", action="/login">
    <center> <h1><font size="+5"> Login Page </font></h1></center><br><br>
    <center><font size="+2"> username: <input style="width:300px;height:40px " style="background-color: whitesmoke" type="text" name ="email" placeholder="example@gmail.com"></font></center><br><br>
    <center><font size="+2"> password: <input style="width:300px;height:40px " style="background-color: whitesmoke" type="text" name ="password" placeholder="**********"></font></center><br><br>
    <center> <input style="width:150px;height:30px " type="submit" name="submit"></center><br><br>
</form>
<center><a href="/registration-page.jsp">Registration</a></center><br><br>
<center><a href="/forgot-password-page.jsp">Forgot password</a></center><br><br>
</body>
</html>
