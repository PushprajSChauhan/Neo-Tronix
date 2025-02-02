<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="in.neotronix.dao.impl.*, in.neotronix.pojo.*,in.neotronix.dao.*,java.util.*"%>

<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Shipped Items</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="shippedItems.css" rel="stylesheet">
  </head>
  <body>
    <jsp:include page="header.jsp" />

    <div class="text-center h3 m-3 animate__animated animate__pulse" style="color: gold; font-size: 2rem">Shipped Orders</div>
        <%
            String status = (String) request.getAttribute("shipmentStatus");
            if (status.equalsIgnoreCase("Order has been shipped successfully")) {
        %>
            <p class="text-center"> 
                <%=status%>
            </p> 
        <%
            }
        %>
    <div class="container-fluid mt-5 mb-5">
        <div class="table-responsive">
            <table class="table table-hover table-sm">
                <thead>
                    <tr class="text-center">
                        <th>Transaction ID</th>
                        <th>Product ID</th>
                        <th>Username</th>
                        <th>Address</th>
                        <th>Quantity bought</th>
                        <th>Amount</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                            List<OrderPojo> orders = (List<OrderPojo>) request.getAttribute("orders");
                            Map<String, String> userIdMap = (Map<String, String>) request.getAttribute("userId");
                            Map<String, String> userAddressMap = (Map<String, String>) request.getAttribute("userAddress");
                            int count = 0;
                            for (OrderPojo order : orders) {
                                String transId = order.getOrderId();
                                String prodId = order.getProdId();
                                int quantity = order.getQuantity();
                                int shipped = order.getShipped();
                                String userid = userIdMap.get(transId);
                                String userAddr = userAddressMap.get(userid);
                                if (shipped != 0) {
                                    count++;
                        %>
                        <tr class="text-center">
                            <td><%=transId%></td>
                            <td><a href="./updateProduct.jsp?prodid=<%=prodId%>"><%=prodId%></a></td>
                            <td><%=userid%></td>
                            <td><%=userAddr%></td>
                            <td><%=quantity%></td>
                            <td>Rs. <%=order.getAmount()%></td>
                            <td class="text-success">SHIPPED</td>
                        </tr>
                        <%
                                }
                            }
                        %>
                        <%
                            if (count == 0) {
                        %>
                        <tr class="text-center no-items-row">
                            <td colspan="7" class="no-items">No Items Available</td>
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