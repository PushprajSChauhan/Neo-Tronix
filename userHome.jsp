<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="in.neotronix.dao.impl.*, in.neotronix.pojo.*, in.neotronix.utility.*, in.neotronix.dao.*, java.util.*, javax.servlet.ServletOutputStream.*, java.io.*" %>

<!DOCTYPE html>
<html>
    <head>
        <title><%=AppInfo.appName%> Application</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

        <!--<link rel="stylesheet" href="mycss.css">-->
        <link rel="stylesheet" href="userHome.css">
    </head>
    <body>

        <jsp:include page="header.jsp" />
        <%
            /* Java Code */
            if (request.getAttribute("message") != null) {
        %>
        <h2 style="color: gold; font-size: 2rem" class="text-center m-3 animate__animated animate__pulse">
            <%=request.getAttribute("message")%>
        </h2>
        <%
            }
        %>
        <!-- Start of Product Items List -->
        <div class="container">
            <div class="row text-center">
                <%
//                    Map<String, Integer> map = (HashMap)request.getAttribute("map");
//                    String username = (String)request.getAttribute("username");
//                    List<ProductPojo> prodList = (ArrayList<ProductPojo>)request.getAttribute("products");
                    Map<String, Integer> map = (HashMap) session.getAttribute("map");
                    String username = (String) session.getAttribute("username");
                    List<ProductPojo> prodList = (ArrayList<ProductPojo>) session.getAttribute("products");

                    for (ProductPojo prod : prodList) {
                        int cartQty = map.get(prod.getProdId());
                %>
                <div class="col-sm-4">
                    <div class="box mt-3 mb-3">
                        <img src="./ShowImageServlet?pid=<%=prod.getProdId()%>" alt="Product"
                             class="mt-3">
                        <p class="productname mt-4"><%=prod.getProdName()%>
                        </p>
                        <%
                            String description = prod.getProdInfo();
                            description = description.substring(0, Math.min(100, description.length()));
                        %>
                        <p class="productinfo">
                            <%=description%>..
                        </p>
                        <p class="price mb-3">
                            Rs <%=prod.getProdPrice()%>
                        </p>
                        <%
                            if (cartQty == 0) {
                        %>
                        <div>
                            <form method="POST" action="./AddToCartServlet" style="display: inline;">
                                <input type="hidden" name="uid" value="<%=username%>">
                                <input type="hidden" name="pid" value="<%=prod.getProdId()%>">
                                <input type="hidden" name="pqty" value="1">
                                <button type="submit" class="btn btn-warning" name="action" value="add">Add to Cart</button>
                            </form>
                            &nbsp;&nbsp;&nbsp;
                            <form method="POST" action="./AddToCartServlet" style="display: inline;">
                                <input type="hidden" name="uid" value="<%=username%>">
                                <input type="hidden" name="pid" value="<%=prod.getProdId()%>">
                                <input type="hidden" name="pqty" value="1">
                                <button type="submit" class="btn btn-primary" name="action" value="buy">Buy Now</button>
                            </form>
                        </div>
                        <%
                        } else {
                        %>
                        <div>
                            <form method="POST" action="./AddToCartServlet"  style="display: inline;">
                                <input type="hidden" name="uid" value="<%=username%>">
                                <input type="hidden" name="pid" value="<%=prod.getProdId()%>">
                                <input type="hidden" name="pqty" value="0">
                                <button type="submit" class="btn btn-danger" name="action" value="remove">Remove From Cart</button>
                            </form>
                            &nbsp;&nbsp;&nbsp;

                            <form method="POST" action="./CartDetailsServlet"  style="display: inline;">
                                <input type="hidden" name="uid" value="<%=username%>">
                                <button type="submit" class="btn btn-success">Checkout</button>
                            </form>
                        </div>
                        <%
                            }
                        %>
                        <br />
                    </div>
                </div>

                <%
                    }
                %>

            </div>
        </div>
        <!-- End of Product Items List -->


        <%@ include file="footer.jsp"%>

    </body>
</html>
