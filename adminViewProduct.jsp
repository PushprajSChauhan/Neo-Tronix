<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*, in.neotronix.pojo.*, in.neotronix.dao.*, in.neotronix.dao.impl.*, javax.servlet.*, java.io.*" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Admin View Products</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
      crossorigin="anonymous"
    />
    <link rel="stylesheet" href="adminViewProduct.css" />
  </head>
  <body>
    <jsp:include page="header.jsp" />

    <h2 class="text-center heading m-3 animate__animated animate__pulse" style="color: gold; font-size: 2rem">
        <%=request.getAttribute("message")%>
    </h2>
    <div class="container">
      <div class="row text-center">
        <%
            List<ProductPojo> products = (ArrayList<ProductPojo>) request.getAttribute("products");
            for (ProductPojo product : products) {
        %>
        <div class="col-sm-4">
          <div class="box mt-3 mb-3">
            <img src="./ShowImageServlet?pid=<%=product.getProdId()%>" alt="Product" 
                 class="product-image mt-3">
            <p class="productname mt-4"><%=product.getProdName()%>
              (<%=product.getProdId()%>)
            </p>
            <p class="productinfo"><%=product.getProdInfo()%></p>
            <p class="price mb-3">
              Rs <%=product.getProdPrice()%>
            </p>
            <div class="buttons">
              <form method="POST" action="./RemoveProductServlet" class="remove-form">
                  <input type="hidden" name="prodid" value="<%=product.getProdId()%>">
                  <button type="submit" class="btn btn-danger">Remove Product</button>
              </form>
              <form method="POST" action="./UpdateProductByIdServlet" class="update-form">
                  <input type="hidden" name="prodid" value="<%=product.getProdId()%>">
                  <button type="submit" class="btn btn-primary">Update Product</button>
              </form>
            </div>
          </div>
        </div>
        <%
            }
        %>
      </div>
    </div>

    <%@ include file="footer.jsp"%>
  </body>
</html>
