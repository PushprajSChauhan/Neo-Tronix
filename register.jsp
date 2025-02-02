<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Document</title>
        <link rel="stylesheet" href="register.css" />
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
            crossorigin="anonymous"
            />

    </head>
    <body>
        <%
            String message = (String) request.getAttribute("status");
            Boolean password = (Boolean) request.getAttribute("password");
            if (password != null && !password) {
        %>
        <script type="text/javascript">
            alert("Passwords do not match. Please re-enter.");
        </script>
        <%
            }
        %>
        <jsp:include page="header.jsp"/>
        <div class="container">
            <h2 class="text-center mb-3 mt-3 animate__animated animate__pulse" style="color: gold">Registration Form</h2>
            <%
                if (message != null) {
            %>
            <p class="text-center"><%=message%></p>
            <%
                }
            %>
            <div class="row justify-content-center">
                <form action="./RegistrationServlet" class="col-md-6 myform" method="post">
                    <div class="row">
                        <div class="col-md-6 form-group">
                            <label for="username">Name</label>
                            <input type="text" name="username" class="form-control" required id="username" />
                        </div>

                        <div class="col-md-6 form-group">
                            <label for="useremail">Email</label>
                            <input
                                type="email"
                                name="useremail"
                                class="form-control"
                                id="useremail"
                                required
                                />
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="address">Address</label>
                        <textarea name="address" id="address" required class="form-control"></textarea>
                    </div>

                    <div class="row">
                        <div class="col-md-6 form-group">
                            <label for="mobile">Mobile No.</label>
                            <input type="number" name="mobile" class="form-control" required id="mobile" />
                        </div>

                        <div class="col-md-6 form-group">
                            <label for="pincode">Pincode</label>
                            <input type="number" name="pincode" class="form-control" id="pincode" required/>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 form-group">
                            <label for="password">Password</label>
                            <input
                                type="password"
                                name="password"
                                class="form-control"
                                id="password"
                                required
                                />
                        </div>

                        <div class="col-md-6 form-group">
                            <label for="cpassword">Confirm Password</label>
                            <input
                                type="password"
                                name="cpassword"
                                class="form-control"
                                id="cpassword"
                                required
                                />
                        </div>
                    </div>

                    <div class="row mt-3 mb-3">
                        <div class="col-md-6 button">
                            <button type="reset" class="btn btn-danger">Reset</button>
                        </div>

                        <div class="col-md-6 button">
                            <button type="submit" class="btn btn-primary">Register</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <jsp:include page="footer.jsp"/>
    </body>
</html>

<!-- CSS CODE
/* register.css */
.error-message {
  color: crimson;
} -->
