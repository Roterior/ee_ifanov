<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Flower Shop - Registration</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.7.0/css/all.css' integrity='sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ' crossorigin='anonymous'>
</head>
<body style="background-color: #999; height: 100vh">
    <div class="bg-dark">
        <div class="container">
            <i class='fas fa-seedling' style='font-size:48px;color:white'></i>
            <h3 class="text-white d-inline-block">Flower Shop</h3>
        </div>
    </div>
    <div>
        <form action="register" class="container text-center pt-5 w-50" method="post">
            <div class="row">
                <div class="form-group col-sm">
                    <label class="h4 font-weight-bold" for="login">Login:</label>
                    <input type="text" class="form-control" id="login" name="login">
                </div>
                <div class="form-group col-sm">
                    <label class="h4 font-weight-bold" for="password">Password:</label>
                    <input type="password" class="form-control" id="password" name="password">
                </div>
            </div>
            <div class="row">
                <div class="form-group col-sm">
                    <label class="h4 font-weight-bold" for="fname">First name*:</label>
                    <input type="text" class="form-control" id="fname" name="fname">
                </div>
                <div class="form-group col-sm">
                    <label class="h4 font-weight-bold" for="lname">Last name*:</label>
                    <input type="text" class="form-control" id="lname" name="lname">
                </div>
            </div>
            <div class="row">
                <div class="form-group col-sm">
                    <label class="h4 font-weight-bold" for="lname">Middle name*:</label>
                    <input type="text" class="form-control" id="mname" name="mname">
                </div>
                <div class="form-group col-sm">
                    <label class="h4 font-weight-bold" for="lname">Phone number*:</label>
                    <input type="number" class="form-control" id="phonenumber" name="phonenumber">
                </div>
            </div>
            <div class="row">
                <div class="form-group col-sm">
                    <label class="h4 font-weight-bold" for="lname">Address*:</label>
                    <input type="text" class="form-control" id="address" name="address">
                </div>
            </div>
            <a href="login" class="btn btn-dark">Back</a>
            <button type="submit" class="btn btn-dark">Create</button>
            <div><kbd>* - Fields with sign are optional</kbd></div>
            <%
                String error = request.getAttribute("error2") != null ? request.getAttribute("error2").toString() : null;
                if (error != null) out.print("<div><kbd class=\"text-danger\">" + error + "</kbd></div>");
            %>
        </form>
    </div>
</body>
</html>
