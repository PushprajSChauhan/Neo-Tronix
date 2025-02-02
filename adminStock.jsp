<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="in.neotronix.dao.impl.*,in.neotronix.dao.*,in.neotronix.pojo.*,javax.servlet.ServletOutputStream,java.io.*,java.util.*" %>

<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Admin Stock</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="adminStock.css">
  </head>
  <body>
    <jsp:include page="header.jsp" />
    <h2 class="text-center mt-3 mb-3 animate__animated animate__pulse" style="color:gold">Product Stock</h2>
    <div class="container-fluid">
        <div class="table-responsive">
            <table class="table table-hover table-sm">
                <thead>
                    <tr>
                        <th>Product Image</th>
                        <th>Product ID</th>
                        <th>Product Name</th>
                        <th>Product Type</th>
                        <th>Product Price</th>
                        <th>Quantity Sold</th>
                        <th>Quantity in Stock</th>
                        <th colspan="2" class="text-center">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                            List<ProductPojo> products=(List<ProductPojo>)request.getAttribute("products");
                            OrderDAO orderDao=new OrderDAOImpl();
                            
                            for(ProductPojo prod:products){
                        %>
                    <tr>
                        <td><img src="./ShowImageServlet?pid=<%=prod.getProdId()%>" class="product-image" style="height: 150px; max-width: 180px"></td>
                        <td><a href="./updateProduct.jsp?prodid=<%=prod.getProdId()%>"><%=prod.getProdId()%></a></td>
                        <%
                            String name=prod.getProdName();
                            name=name.substring(0,Math.min(name.length(),25));
                        %>
                        <td><%=name%></td>
                        <td><%=prod.getProdType().toUpperCase()%></td>
                        <td><%=prod.getProdPrice()%></td>
                        <td><%=orderDao.getSoldQuantity(prod.getProdId())%></td>
                        <td><%=prod.getProdQuantity()%></td>
                        <td>
                            <form method="post">
                                <button type="submit"
                                        formaction="./UpdateProductByIdServlet?prodid=<%=prod.getProdId()%>"
                                        class="btn btn-primary">Update</button>
                            </form>
                        </td>
                        <td>
                            <form method="post">
                                <button type="submit"
                                        formaction="./RemoveProductServlet?prodid=<%=prod.getProdId()%>"
                                        class="btn btn-danger">Remove</button>
                            </form>
                        </td>
                    </tr>
                    <%
                            }
                        %>
                    <%
                        if(products.isEmpty()) {
                    %>
                    <tr class="no-items-row">
                        <td class="no-items" colspan="8">No Items Available</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
    </div>

    <%@ include file="footer.jsp"%>
  </body>
</html>
