<%@ page import="com.digi.model.User" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.digi.model.Address" %>
<%@ page import="com.digi.repository.AddressRepositoryImpl" %>
<%@ page import="com.digi.repository.AddressRepositoryJPAImpl" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account</title>
    <head>
        <title>Welcome page</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css"
              integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
              crossorigin="anonymous">
        <style>
            body {
                margin-top: 20px;
                color: #1A202C;
                text-align: left;
                background-color: #E2E8F0;
            }

            .main-body {
                padding: 15px;
            }

            .card {
                box-shadow: 0 1px 3px 0 rgba(0, 0, 0, .1), 0 1px 2px 0 rgba(0, 0, 0, .06);
            }

            .card {
                position: relative;
                display: flex;
                flex-direction: column;
                min-width: 0;
                word-wrap: break-word;
                background-color: #fff;
                background-clip: border-box;
                border: 0 solid rgba(0, 0, 0, .125);
                border-radius: .25rem;
            }

            .card-body {
                flex: 1 1 auto;
                min-height: 1px;
                padding: 1rem;
            }

            .gutters-sm {
                margin-right: -8px;
                margin-left: -8px;
            }

            .gutters-sm > .col, .gutters-sm > [class*=col-] {
                padding-right: 8px;
                padding-left: 8px;
            }

            .mb-3 {
                margin-bottom: 1rem !important;
            }
        </style>
    </head>
        <%
            User user = (User) session.getAttribute("user");
            if (user == null) {
            response.sendRedirect("/home-page.jsp");
            return;
            }
        AddressRepositoryJPAImpl aJpa = new AddressRepositoryJPAImpl();
        AddressRepositoryImpl addressRepository = new AddressRepositoryImpl();
        Address address = aJpa.getAddressByUserId(user.getId());
        String addressStr = "";
        if (address != null) {
            addressStr = address.getCountry() + " , " + address.getCity() + " , " + address.getStreet() + " , " + address.getHome();
        }
    %>
<body>
<h1 class="center">Account Page</h1>
</br>
<hr>
<div class="container">
    <div class="main-body">
        <div class="row gutters-sm">
            <div class="col-md-4 mb-3">
                <div class="card">
                    <div class="card-body">
                        <div class="d-flex flex-column align-items-center text-center">
                            <img src="https://bootdey.com/img/Content/avatar/avatar7.png" alt="Admin"
                                 class="rounded-circle" width="150">
                            <div class="mt-3">
                                <h4><%=user.getName() + " " + user.getSurname()%>
                                </h4>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-8">
                <div class="card mb-3">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">Name</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                <%= user.getName()%>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">SurName</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                <%= user.getSurname()%>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">Year</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                <%= user.getYear()%>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">Email</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                <%= user.getEmail()%>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">Address</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                <%= addressStr %>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-12">
                                <a class="btn btn-info " href="/edit-user-info-page.jsp">Edit</a>
                                <% if (address == null) { %>
                                <%= "<a class='btn btn-info' href='/add-address-page.jsp'>Add address</a>" %>
                                <% } %>
                                <a class="btn btn-info " href="/change-password-page.jsp">Change password</a>
                                <a class="btn btn-info " href="/delete-account-page.jsp">Delete Account</a>

                                <form method="get" action="/logout">
                                    <input class="btn btn-info " type="submit" value="Logout">
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<hr>
</body>
</html>