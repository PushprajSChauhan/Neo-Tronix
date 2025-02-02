<%@page import="java.util.Map, java.util.HashMap, java.util.*"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="in.neotronix.utility.*, in.neotronix.pojo.ProductPojo, in.neotronix.dao.impl.*, in.neotronix.dao.*" %>
<%@page import="in.neotronix.pojo.*, javax.servlet.ServletOutputStream, java.io.*, in.neotronix.dao.impl.ProductDAOImpl" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title><%=AppInfo.appName%> Application</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="index.css">
    </head>
    <body>
        <jsp:include page="header.jsp" />

        <h2 class="text-center heading m-3 animate__animated animate__pulse" style="color: gold; font-size: 2rem;">
            <%=request.getAttribute("message")%>
        </h2>
        <div class="container">
            <div class="row text-center">
                <%
                    Map<String, Integer> map = (HashMap) request.getAttribute("map");
                    String username = (String) request.getAttribute("username");
                    List<ProductPojo> prodList = (ArrayList<ProductPojo>) request.getAttribute("products");

                    for (ProductPojo prod : prodList) {
                        int cartQty = map.get(prod.getProdId());
                %>
                <div class="col-sm-4">
                    <div class="box mt-3 mb-3">
                        <img src="./ShowImageServlet?pid=<%=prod.getProdId()%>" alt="Product" class="mt-3" />
                        <p class="productname mt-4">
                            <%=prod.getProdName()%>
                        </p>
                        <%
                            String description = prod.getProdInfo();
                            description = description.substring(0, Math.min(100, description.length()));
                        %>
                        <p class="productinfo">
                            <%=description%>
                            ..</p>
                        <p class="price mb-3">
                            Rs <%=prod.getProdPrice()%></p>
                        <form method="post">
                            <%
                                if (cartQty == 0) {
                            %>
                            <div class="buttons mb-2">
                                <button
                                    type="submit"
                                    formaction="./AddToCartServlet?uid=<%=username%>&pid=<%=prod.getProdId()%>&pqty=1"
                                    class="btn btn-warning"
                                    >
                                    Add to Cart
                                </button>
                                &nbsp;&nbsp;&nbsp;
                                <button
                                    type="submit"
                                    formaction="./AddToCartServlet?uid=<%=username%>&pid=<%=prod.getProdId()%>&pqty=1"
                                    class="btn btn-primary"
                                    >
                                    Buy Now
                                </button>
                            </div>
                            <%
                            } else {
                            %>
                            <div class="buttons">
                                <button
                                    type="submit"
                                    formaction="./AddToCartServlet?uid=<%=username%>&pid=<%=prod.getProdId()%>&pqty=0"
                                    class="btn btn-danger"
                                    >
                                    Remove From Cart
                                </button>
                                &nbsp;&nbsp;&nbsp;
                                <button
                                    type="submit"
                                    formaction="cartDetails.jsp"
                                    class="btn btn-success"
                                    >
                                    Checkout
                                </button>
                            </div>
                            <%
                                }
                            %>  
                        </form>
                        <br>
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