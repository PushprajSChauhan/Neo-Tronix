<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="in.neotronix.dao.impl.ProductDAOImpl, java.util.*" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Categories of the Products</title>
        <link rel="stylesheet" href="header.css">
    </head>
    <body>
    <li class="nav-item dropdown" style="position: relative">
        <a
            class="nav-link dropdown-toggle"
            href="#"
            id="dropdownMenuLink"
            role="button"
            data-bs-toggle="dropdown"
            aria-expanded="false"
            >
            Category
        </a>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
            <% /* Java Code to interact with ProductDAO */
                ProductDAOImpl prodDao = new ProductDAOImpl();
                String userType = (String) session.getAttribute("usertype");
                List<String> prodTypes = prodDao.getAllProductsType();
                if (userType != null && userType.equalsIgnoreCase("admin")) {
                    for (String type : prodTypes) {
                        String str = type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();
            %>  
            <li><a href="./AdminViewServlet?type=<%=type%>" class="dropdown-item"><%=str%></a></li>
            <!--DB mein case sensitive form mei data insert rhega toh ussi hisab se request bhejni hai-->
            <%
                }
            } else {
            %>

            <!--            This is for normal user or guest-->
            <% /* Java Code to interact with ProductDAO */
                for (String type : prodTypes) {
                    String str = type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();
            %>  
            <li><a href="./LandingServlet?type=<%=type%>" class="dropdown-item"><%=str%></a></li>
            <!--DB mein case sensitive form mei data insert rhega toh ussi hisab se request bhejni hai-->
            <%
                    }
                }
            %>
        </ul>
    </li>
</body>
</html>
