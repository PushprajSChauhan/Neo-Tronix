<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="in.neotronix.dao.impl.*, in.neotronix.dao.*,in.neotronix.utility.*,java.util.*"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>NeoTronix</title>
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
            crossorigin="anonymous"
            />
        <link rel="stylesheet" href="header.css" />
        <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
            integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg=="
            crossorigin="anonymous"
            referrerpolicy="no-referrer"
            />
        <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"
            />
    </head>
    <body>
        <% /* Checking the user credentials for guest user */ /* Java Code */
            String usertype = (String) session.getAttribute("usertype");
            if (usertype == null) {
                //                        Guest
        %>
        <!-- navbar 1 starts here -->
        <nav class="navbar navbar-expand-lg navbar-custom">
            <div class="container-fluid">
                <!-- logo -->
                <a href="LandingServlet" class="navbar-brand">
                    <strong><%=AppInfo.appName%></strong>
                </a>
                <!-- hamburger icon -->
                <button
                    class="navbar-toggler icon"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#navbarNav"
                    aria-controls="navbarNav"
                    aria-expanded="false"
                    aria-label="Toggle navigation"
                    >
                    <span class="navbar-toggler-icon" id="icon"></span>
                </button>
                <!-- navbar menus -->
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav ms-auto">
                        <jsp:include page="category.jsp" />

                        <li class="nav-item">
                            <a href="./LandingServlet" class="nav-link">Products</a>
                        </li>
                        <li class="nav-item">
                            <a href="login.jsp" class="nav-link">Login</a>
                        </li>
                        <li class="nav-item">
                            <a href="register.jsp" class="nav-link">Register</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-- navbar 1 ends here -->
        <hr />
        <%
        } else if (usertype.equalsIgnoreCase("Customer")) {
            //                    Customer
            String username = (String) session.getAttribute("username");
            CartDAOImpl cartDao = new CartDAOImpl();
            TransactionDAOImpl tranDao = new TransactionDAOImpl();
            int count = cartDao.getAllCartItems(username).size();
        %> 
        <!-- customer navbar starts here -->
        <nav class="navbar navbar-expand-lg navbar-custom">
            <div class="container-fluid">
                <a href="./UserHomeServlet" class="navbar-brand"
                   ><strong><%=AppInfo.appName%></strong></a
                >
                <button
                    class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" 
                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation"
                    >
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav ms-auto">
                        <jsp:include page="category.jsp" /> 
                        <li class="nav-item">
                            <a href="./UserHomeServlet" class="nav-link">Products</a>
                        </li>
                        <%
                            if (count == 0) {
                        %> 
                        <li class="nav-item">
                            <a class="nav-link" href="./CartDetailsServlet"><span class="glyphiconglyphicon-shopping-cart"></span>Cart 
                            </a>
                        </li>
                        <%
                        } else {
                        %> 
                        <li class="nav-item">
                            <a class="nav-link" href="./CartDetailsServlet" id="mycart">
                                <i class="fa fa-shopping-cart fa-2x icon-white cart" style="color: #40e0d0; background-color: #0a1828;">
                                    <span class="badge"><%=count%></span>
                                </i></a>
                        </li>
                        <%
                            }
                        %> 
                        <li class="nav-item"><a class="nav-link" href="./OrderServlet?amount=<%=tranDao.getTotalAmount(username)%>&username=<%=username%>">Orders</a></li>
                        <li class="nav-item"><a class="nav-link" href="./UserProfileServlet">Profile</a></li> 
                        <li class="nav-item"><a class="nav-link" href="./LogoutServlet">Logout</a></li> 
                    </ul>
                </div>
            </div>
        </nav>
        <!-- customer navbar ends here -->
        <hr />
        <%
        } else {
            //            Admin
%> 
        <!-- admin navbar starts here -->
        <nav class="navbar navbar-expand-lg navbar-custom">
            <div class="container-fluid">
                <a href="./AdminViewServlet" class="navbar-brand"
                   ><strong><%=AppInfo.appName%></strong></a
                >
                <button
                    class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" 
                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation"
                    >
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item">
                            <a href="./AdminViewServlet" class="nav-link">Products</a>
                        </li>
                        <li class="nav-item dropdown">
                            <jsp:include page="category.jsp" /> 
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="./AdminStockServlet">Stock</a>
                        </li> 
                        <li class="nav-item">
                            <a class="nav-link" href="./ShippedItemServlet">Shipped</a>
                        </li> 
                        <li class="nav-item">
                            <a class="nav-link" href="./UnshippedItemServlet">Orders</a>
                        </li> 
                        <li class="nav-item dropdown">
                            <a
                                class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" 
                                aria-expanded="false"
                                >Update Item</a
                            >
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown"> 
                                <li><a href="addProduct.jsp" class="dropdown-item">Add Product</a></li> 
                                <li><a href="removeProduct.jsp" class="dropdown-item">Remove Product</a></li> 
                                <li><a href="updateProductById.jsp" class="dropdown-item">Update Product</a></li> 
                            </ul> 
                        </li>
                        <li class="nav-item"><a href="./LogoutServlet" class="nav-link">Logout</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-- admin navbar ends here -->
        <hr />
        <%
            }
        %>
        <!-- search bar starts here -->
        <div class="container-fluid text-center p-3">
            <h1 class="app-name animate__animated animate__fadeInUp"><%=AppInfo.appName%></h1>
            <p class="tag-line  animate__animated animate__fadeInUp">We Specialize in Electronics</p>
            <form class="form-inline" action="LandingServlet" method="get">
                <div class="input-group">
                    <input
                        type="text"
                        class="form-control"
                        name="search"
                        placeholder="Search Items"
                        required
                        />
                    <input type="submit" class="btn" value="Search"/>
                </div>
            </form>
            <p
                align="center"
                style="
                color: blue;
                font-weight: bold;
                margin-top: 5px;
                margin-bottom: 5px;
                "
                id="message"
                >
            </p>
        </div>
        <!-- search bar ends here -->
        <hr class="bottom-hr"/>
    </body>
</html>
