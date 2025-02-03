<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*, java.io.*, in.neotronix.dao.*, in.neotronix.utility.*, in.neotronix.pojo.*, javax.servlet.ServletOutputStream, in.neotronix.dao.impl.*" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Profile Details</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

        <link rel="stylesheet" href="userProfile.css">
    </head>
    <body>

        <%
            UserPojo user = (UserPojo) request.getAttribute("user");
        %>

        <jsp:include page="header.jsp" />

        <div class="container mt-5 user-profile">
            <!--            <div class="row">
                            <div class="col mt-3">
                                <nav aria-label="breadcrumb" class="bg-warning rounded-3 p-3 mb-4">
                                    <ol class="breadcrumb mb-0">
                                        <li class="breadcrumb-item"><a href="new index.jsp">Home</a></li>
                                        <li class="breadcrumb-item"><a href="userProfile.jsp">User
                                                Profile</a></li>
                                    </ol>
                                </nav>
                            </div>
                        </div>-->

            <div class="row">
                <div class="col-lg-4">
                    <div class="card mb-4" style="background-color: #0a1828">
                        <div class="card-body text-center">
                            <img src="images/profile.jpg" class="rounded-circle img-fluid w-25"
                                 >
                            <h5 class="mt-3" style="color: gold">
                                Hello <%=user.getUsername()%> here!!
                            </h5>
                        </div>
                    </div>
                    <div class="card mb-4" style="background-color: #0a1828">
                        <div class="card-body text-center mt-2">
                            <h4>My Profile</h4>
                        </div>
                    </div>
                </div>
                <div class="col-lg-8">
                    <div class="card mb-4" style="background-color: #0a1828">
                        <div class="card-body">
                            <div class="row m-2">
                                <div class="col-sm-3 field">
                                    <p>Full Name</p>
                                </div>
                                <div class="col-sm-9">
                                    <p class="value"><%=user.getUsername()%></p>
                                </div>
                            </div>
                            <div class="row m-2">
                                <div class="col-sm-3 field">
                                    <p>Email</p>
                                </div>
                                <div class="col-sm-9">
                                    <p class="value"><%=user.getUseremail()%>
                                    </p>
                                </div>
                            </div>
                            <div class="row m-2">
                                <div class="col-sm-3 field">
                                    <p>Phone</p>
                                </div>
                                <div class="col-sm-9">
                                    <p class="value"><%=user.getMobile()%>
                                    </p>
                                </div>
                            </div>
                            <div class="row m-2">
                                <div class="col-sm-3 field">
                                    <p>Address</p>
                                </div>
                                <div class="col-sm-9">
                                    <p class="value"><%=user.getAddress()%>
                                    </p>
                                </div>
                            </div>
                            <div class="row m-2">
                                <div class="col-sm-3 field">
                                    <p>PinCode</p>
                                </div>
                                <div class="col-sm-9">
                                    <p class="value"><%=user.getPincode()%>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <br>
        <br>
        <br>

        <%@ include file="footer.jsp"%>

    </body>
</html>