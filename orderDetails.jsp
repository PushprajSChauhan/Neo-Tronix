<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*, java.text.SimpleDateFormat, java.io.*, in.neotronix.dao.*, in.neotronix.utility.*, in.neotronix.pojo.*, javax.servlet.ServletOutputStream, in.neotronix.dao.impl.*" %>

<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Order Details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="orderDetails.css">
  </head>
  <body>
    <%
        List<OrderDetailsPojo> orders = (List<OrderDetailsPojo>) session.getAttribute("orders");
        String username = (String) session.getAttribute("username");
        String paymentStatus = (String) session.getAttribute("paymentStatus");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    %>

    <jsp:include page="header.jsp" />
    <h2 class="text-center mt-3 mb-3 animate__animated animate__pulse" style="color: gold">Order Details</h2>

    <%
        if (paymentStatus != null && !paymentStatus.equals("No orders placed yet") && !paymentStatus.equals("Order Placement Failed!") && username != null) {
    %>
    <p class="text-center">
        <%= paymentStatus %>
    </p>
    <%
        }
    %>
    <div class="container-fluid">
      <div class="table-responsive">
        <table class="table table-hover table-sm">
          <thead>
            <tr class="text-center">
              <th>Product Image</th>
              <th>Product Name</th>
              <th>Order ID</th>
              <th>Quantity</th>
              <th>Price</th>
              <th>Date</th>
              <th>Order Status</th>
            </tr>
          </thead>
          <tbody>
            <%
                if (orders != null && !orders.isEmpty()) { 
                    for (OrderDetailsPojo order : orders) {
            %>
            <tr class="text-center">
              <td><img src="./ShowImageServlet?pid=<%=order.getProdId()%>" height="150px" width="180px"></td>
              <td><%= order.getProdName() %></td>
              <td><%= order.getOrderId() %></td>
              <td><%= order.getQuantity() %></td>
              <td><%= order.getAmount() %></td>
              <td><%= sdf.format(order.getTime()) %></td>
              <td class="text-success"><%= order.getShipped() == 0 ? "ORDER PLACED" : "ORDER SHIPPED" %></td>
            </tr>
            <%    
                    }
                } else {
            %>
            <tr>
              <td colspan="7" class="text-center">No orders found.</td>
            </tr>
            <%
                }
            %>
          </tbody>
        </table>
      </div>
    </div>
    <%@ include file="footer.jsp" %>
  </body>
</html>
<!-- 
CSS CODE
/* Styles for product image in the table */
img {
    width: 50px;
    height: 50px;
}
 -->
