<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Remove Product</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="removeProduct.css">
</head>
<body>
    <%
            /* Checking the user credentials */
            String username = (String) session.getAttribute("username");
            String password = (String) session.getAttribute("password");
            String usertype = (String) session.getAttribute("usertype");

            if (usertype == null || !usertype.equalsIgnoreCase("admin")) {
                response.sendRedirect("login.jsp?message=Access Denied! Please login as Admin");
            } else if (username == null || password == null) {
                response.sendRedirect("login.jsp?message=Session expired! Please login again");
            }
        %> 

        <jsp:include page="header.jsp" /> 

        <%
            String message=(String)request.getParameter("message");
        %> 
    <div class="container">
        <div class="text-center">
            <h3 style="color: gold; font-size: 2rem" class="animate__animated animate__pulse">Product Deletion Form</h3> 
            <%         
                if(message!=null){
            %> 
            <p class="message"> 
                <%=message%> 
            </p> 
            <%
                }
            %> 
        </div>
        <div class="row justify-content-center">
            <form action="./RemoveProductServlet" class="myform col-md-4 mt-3 col-md-offset-4">
                <div class="row mt-3">
                    <div class="col-md-12 form-group">
                        <label for="product_id">Product Id</label>
                        <input type="text" placeholder="Enter Product Id" class="form-control mt-2 mb-2" id="product_id" name="prodid" required>
                    </div>
                </div>
                <div class="row mt-3 mb-3 text-center">
                    <div class="col-md-6">
                        <a href="./AdminViewServlet" class="btn btn-info">Cancel</a> 
                    </div>
                    <div class="col-md-6">
                        <button type="submit" class="btn btn-danger">Remove</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <%@ include file="footer.jsp"%> 
</body>
</html>

<!-- CSS CODE
/* removeProduct.css */

/* Style for the message */
.message {
    color: blue;
}