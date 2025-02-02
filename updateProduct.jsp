<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
<%@page import="in.neotronix.dao.impl.*, in.neotronix.pojo.*, in.neotronix.dao.*, java.util.*, javax.servlet.ServletOutputStream, java.io.*" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Update Product</title>
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
            crossorigin="anonymous"
            />
        <link rel="stylesheet" href="updateProduct.css" />
    </head>
    <body>
        <jsp:include page="header.jsp" /> 

        <%
            String message = (String) request.getParameter("message");
            ProductPojo product = (ProductPojo) request.getAttribute("product");
            String pid = request.getParameter("prodid");
            ProductDAOImpl prodDao=new ProductDAOImpl();
            if(product==null){
                product=prodDao.getProductDetails(pid);
            }
            if (product != null) {
                pid = product.getProdId();
            }
        %> 
        <div class="container">
            <div class="row">
                <div class="form-group col-md-12 text-center">
                    <img src="./ShowImageServlet?pid=<%=pid%>" alt="Product Image" class="product-image" height="200px" width="200px"/> 
                    <h2 class="mt-2 animate__animated animate__pulse" style="color: gold">Product Update</h2>
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
            </div>
            <div class="row justify-content-center">
                <form action="./UpdateProductServlet" method="post" class="myform col-md-6">

                    <div class="row"> 
                        <input type="hidden" name="pid" class="form-control" value="<%=pid%>" id="last_name"> 
                    </div> 

                    <div class="row mt-3">
                        <div class="col-md-6 form-group mb-2">
                            <label for="product_name">Product Name</label>
                            <input type="text" placeholder="Enter Product Name" name="name" class="form-control" value="<%=product.getProdName()%>" id="product_name" required> 
                        </div>

                        <div class="col-md-6 form-group mb-2">
                            <%
                                String prodType = product.getProdType();
                            %> 
                            <label for="product_type">Product Type</label>
                            <select name="type" id="product_type" class="form-control" required>
                                <option value="mobile" <%=prodType.equalsIgnoreCase("mobile") ? "selected" : ""%>>MOBILE</option> 
                                <option value="tv" <%=prodType.equalsIgnoreCase("tv") ? "selected" : ""%>>TV</option> 
                                <option value="camera" <%=prodType.equalsIgnoreCase("camera") ? "selected" : ""%>>CAMERA</option> 
                                <option value="laptop" <%=prodType.equalsIgnoreCase("laptop") ? "selected" : ""%>>LAPTOP</option> 
                                <option value="tablet" <%=prodType.equalsIgnoreCase("tablet") ? "selected" : ""%>>TABLET</option> 
                                <option value="speaker" <%=prodType.equalsIgnoreCase("speaker") ? "selected" : ""%>>SPEAKER</option> 
                                <option value="other" <%=prodType.equalsIgnoreCase("other") ? "selected" : ""%>>Some Other Appliances</option> 
                            </select>
                        </div>
                    </div>

                    <div class="form-group mb-2">
                        <label for="product_desc">Product Description</label>
                        <textarea name="info" class="form-control text-align-left" id="last_name" required><%=product.getProdInfo()%></textarea> 
                    </div>

                    <div class="row mt-3">
                        <div class="form-group col-md-6 mb-2">
                            <label for="unit_price">Unit Price</label>
                            <input type="number" value="<%=product.getProdPrice()%>" placeholder="Enter Unit Price" name="price" class="form-control" id="unit_price" required> 
                        </div>

                        <div class="form-group col-md-6 mb-2">
                            <label for="stock_quantity">Stock Quantity</label>
                            <input type="number" value="<%=product.getProdQuantity()%>" placeholder="Enter Stock Quantity" class="form-control" id="stock_quantity" name="quantity" required> 
                        </div>
                    </div>

                    <div class="row text-center mt-3">
                        <div class="col-md-6 button">
                            <button type="reset" class="btn btn-danger" formaction="./AdminViewServlet">Cancel</button>
                        </div>

                        <div class="col-md-6 button">
                            <button type="submit" class="btn btn-primary">Update</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <%@ include file="footer.jsp"%> 
    </body>
</html>
