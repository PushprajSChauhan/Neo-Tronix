<%@ page language="java" contentType="text/html; charset=ISO-8859-1" 
         pageEncoding="ISO-8859-1"%> 

<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Update Product By ID</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="updateProductById.css">
  </head>
  <body>
    <%
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
            /* Java Code */
            String message=(String)request.getAttribute("message");
        %> 
    <div class="container">
        <div class="text-center"> 
            <h3 style="color: gold; font-size: 2rem" class="animate__animated animate__pulse">Product Update Form</h3> 
            <% 
                /* Java Code */
                if(message!=null){
            %> 
            <p class="text-danger"> 
                <%=message%> 
            </p> 
            <%
                /* Java Code */
                }
            %> 
        </div> 
        <div class="row justify-content-center mt-3">
            <form action="./UpdateProductByIdServlet" method="post" class="col-md-4 myform">
                <div class="row mt-3 mb-3">
                    <div class="form-group col-md-12">
                        <label for="product_id">Product ID</label>
                        <input type="text" placeholder="Enter Product Id" name="prodid" class="form-control" 
                                                                            id="product_id" required> 
                    </div>
                </div>

                <div class="row text-center">
                    <div class="col-md-6">
                        <a href="./AdminViewServlet" class="btn btn-danger">Cancel</a>
                    </div>
                    
                    <div class="col-md-6 ">
                        <button type="submit" class="btn btn-primary">Update Product</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <%@ include file="footer.jsp"%> 
  </body>
</html>
