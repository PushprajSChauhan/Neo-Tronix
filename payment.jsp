<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Payments</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="payment.css">
    </head>
    <body>
        <%
            String username = (String) session.getAttribute("username");
            String password = (String) session.getAttribute("password");

            if (username == null || password == null) {
                response.sendRedirect("login.jsp?message=Session expired! Please login again");
            }

            String sAmount = request.getParameter("amount");
            double amount = 0.0;
            if (sAmount != null) {
                amount = Double.parseDouble(sAmount);
            }

            java.util.Date today = new java.util.Date();
            int currYear = today.getYear() + 1900;
        %>

        <jsp:include page="header.jsp" />
        <div class="container">
            <div class="text-center mt-3 mb-3">
                <img src="payment.png" alt="" class="payment-img mb-3 animate__animated animate__pulse">
                <h2 style="color: gold; font-size: 2rem" class="animate__animated animate__pulse">Payment Details</h2>
            </div>
            <div class="row justify-content-center">
                <form action="./OrderServlet?username=<%=username%>" class="col-md-6 myform col-md-offset-3" method="post">

                    <div class="row mt-3">
                        <div class="form-group col-md-12">
                            <label for="card_holder_name">Name of Card holder</label>
                            <input type="text" id="card_holder_name" class="form-control" required placeholder="Enter Card Holder Name" name="cardholder">
                        </div>
                    </div>

                    <div class="row mt-3">
                        <div class="form-group col-md-12">
                            <label for="card_number">Credit Card Number</label>
                            <input type="number" id="card_number" class="form-control" placeholder="1234-1234-1234-1234" required name="cardnumber">
                        </div>
                    </div>

                    <div class="row mt-3">
                        <div class="col-md-6 form-group">
                            <label for="expiry_month">Expiry Month</label>
                            <input type="number" name="expmonth" id="expiry_month" class="form-control" min="1" max="12" size="2" required placeholder="MM">
                        </div>

                        <div class="col-md-6 form-group">
                            <label for="expiry_year">Expiry Year</label>
                            <input type="number" placeholder="YYYY" size="4" name="expyear" min="<%=currYear%>" id="expiry_year" class="form-control" required>
                        </div>
                    </div>

                    <div class="row mt-3 mb-3">
                        <div class="col-md-6 form-group">
                            <label for="cvv">Card CVV</label>
                            <input type="number" size="3" name="cvv" placeholder="123" id="cvv" class="form-control" required>
                            <input type="hidden" name="amount" value="<%=amount%>">
                        </div>

                        <div class="col-md-6 form-group text-center">
                            <label for="">&nbsp;</label>
                            <button type="submit" class="form-control btn btn-warning">
                                Pay :Rs <%=amount%></button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <%@ include file="footer.jsp"%>
    </body>
</html>