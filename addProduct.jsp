<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Add Product</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
      crossorigin="anonymous"
    />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
      integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg=="
      crossorigin="anonymous"
      referrerpolicy="no-referrer"
    />
    <link rel="stylesheet" href="addProduct.css" />
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
        String message = (String) request.getAttribute("message");
    %> 
    <div class="container">
      <div class="text-center"> 
        <h2 style="color: gold; font-size: 2rem" class="mb-2 animate__animated animate__pulse">Product Addition Form</h2> 
        <%
            if (message != null) {
        %> 
        <p> 
            <%=message%>
        </p> 
        <%
            }
        %> 
      </div> 
      <div class="row justify-content-center mt-2">
        <form action="./AddProductServlet" class="col-md-6 myform shadow p-4 rounded col-md-offset-3" method="POST" enctype="multipart/form-data">
          <div class="row mt-3">
            <div class="col-md-6 form-group"> 
              <label for="product_name">Product Name</label>
              <input type="text" placeholder="Enter Product Name" name="name" class="form-control mb-2" id="product_name" required> 
            </div> 

            <div class="col-md-6 form-group"> 
              <label for="producttype">Product Type</label>
              <select name="type" id="producttype" class="form-control" required> 
                <option value="mobile">MOBILE</option> 
                <option value="tv">TV</option> 
                <option value="camera">CAMERA</option> 
                <option value="laptop">LAPTOP</option> 
                <option value="tablet">TABLET</option> 
                <option value="speaker">SPEAKER</option> 
                <option value="other">Some Other Appliances</option> 
              </select> 
            </div> 
          </div>

          <div class="form-group mt-3"> 
            <label for="prod_desc">Product Description</label> 
            <textarea name="info" class="form-control" id="prod_desc" required></textarea> 
          </div> 

          <div class="row mt-3">
            <div class="col-md-6 form-group"> 
              <label for="unit_price">Unit Price</label>
              <input type="number" placeholder="Enter Unit Price" name="price" class="form-control mb-2" id="unit_price" required> 
            </div> 

            <div class="col-md-6 form-group"> 
              <label for="stock_qty">Stock Quantity</label>
              <input type="number" placeholder="Enter Stock Quantity" name="quantity" class="form-control mb-2" id="stock_qty" required> 
            </div> 
          </div>

          <div class="mt-3"> 
            <div class="col-md-12 form-group"> 
              <label for="prod_img">Product Image</label>
              <input type="file" placeholder="Select Image" name="image" class="form-control" id="prod_img" required> 
            </div> 
          </div> 

          <div class="row text-center mt-4 buttons">
            <div class="col-md-6">
              <button type="reset" class="btn btn-danger">Reset</button>
            </div>

            <div class="col-md-6">
              <button type="submit" class="btn btn-primary">Add</button>
            </div>
          </div>
        </form>
      </div>
    </div>

    <%@ include file="footer.jsp"%> 
  </body>
</html>
