<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
<%@page import="in.neotronix.dao.impl.*, in.neotronix.dao.*, in.neotronix.pojo.*, javax.servlet.ServletOutputStream, java.io.*, java.util.*" %>

<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Cart Items</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="cartDetails.css">
  </head>
  <body>
    <jsp:include page="header.jsp" /> 
    <h2 class="text-center mt-3 mb-3 animate__animated animate__pulse" style="color: gold">
        Cart Items
    </h2>
    <div class="container-fluid">
        <div class="table-responsive">
            <table class="table table-hover">
                <thead>
                    <tr class="text-center">
                        <th>Product Image</th>
                        <th>Products</th>
                        <th>Product Price</th>
                        <th>Product Quantity</th>
                        <th>Add</th>
                        <th>Remove</th>
                        <th>Amount</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        String username = (String) request.getAttribute("username");
                        List<CartPojo> cartItems = (List<CartPojo>) request.getAttribute("cartItems");
                        if (cartItems.isEmpty()) {
                    %>
                        <p class="text-center"> 
                            Your cart is currently empty. Browse our wide range of products and enjoy an exceptional shopping experience!
                        </p> 
                <%
                    }
                    Map<String, Object> map = (Map<String, Object>) request.getAttribute("map");
                    double totalAmount = 0.0;
                    for (CartPojo item : cartItems) {
                        String prodId = item.getProdId();
                        int prodQuantity = item.getQuantity();
                        ProductPojo product = (ProductPojo) map.get(prodId);
                        double prodPrice = product.getProdPrice();
                        double currAmount = prodQuantity * prodPrice;
                        totalAmount += currAmount;
                        if (prodQuantity > 0) {
                %> 
                <tr class="text-center">
                         <td><img src="./ShowImageServlet?pid=<%=product.getProdId()%>" height="150px" width="180px"></td> 
                        <td><%=product.getProdName()%></td> 
                        <td><%=product.getProdPrice()%></td> 
                        <td>
                            <form method="post" action="./UpdateCartServlet"> 
                                <input type="number" name="pqty" value="<%=prodQuantity%>" class="quantity-input" min="0">
                                <input type="hidden" name="pid" value="<%=product.getProdId()%>">
                                <input type="submit" name="Update" value="Update" class="update-button"> 
                            </form>
                        </td> 
                        <td><a href="./CartDetailsServlet?add=1&uid=<%=username%>&pid=<%=product.getProdId()%>&avail=<%=product.getProdQuantity()%>&qty=<%=prodQuantity%>"><i class="fa fa-plus"></i></a></td> 
                        <td><a href="./CartDetailsServlet?add=0&uid=<%=username%>&pid=<%=product.getProdId()%>&avail=<%=product.getProdQuantity()%>&qty=<%=prodQuantity%>"><i class="fa fa-minus"></i></a></td> 
                        <td><%=currAmount%></td> 
                     </tr>
                     <%
                        }
                    }
                %> 
                     <tr>
                        <td colspan="6" class="text-center">Total Amount to Pay (in Rupees)</td> 
                        <td><%=totalAmount%></td> 
                     </tr>
                     <%
                    if (totalAmount != 0) {
                %> 
                     <tr class="text-center">
                        <td colspan="4"> 
                            <td>
                                <form method="post"> 
                                    <button formaction="./UserHomeServlet" class="btn btn-dark">Cancel</button> 
                                </form>
                            </td> 
                            <td colspan="2" align="center">
                                <form method="post"> 
                                    <button class="btn btn-warning" formaction="payment.jsp?amount=<%=totalAmount%>">Pay Now</button> 
                                </form>
                            </td>
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
