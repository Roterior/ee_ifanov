<%@ page import="com.accenture.flowershop.be.entity.Purchase" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Flower Shop</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.7.0/css/all.css' integrity='sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ' crossorigin='anonymous'>
</head>
<body style="background-color: #999">
    <div class="bg-dark">
        <div class="container">
            <i class='fas fa-seedling' style='font-size:48px;color:white'></i>
            <h3 class="text-white d-inline-block">Flower Shop</h3>
        </div>
    </div>
    <div class="container pt-1 w-50">
        <form class="d-inline-block" action="logout" method="post">
            <input class="btn btn-dark" type="submit" value="Logout">
        </form>
        <table class="table table-sm pt-2">
            <thead class="thead-dark">
            <tr>
                <th>Login</th>
                <th>Summary</th>
                <th>Created</th>
                <th>Closed</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Purchase> purchases = (List<Purchase>) session.getAttribute("purchaseListAll");
                if (purchases != null) {
                    for (Purchase purchase: purchases) {
            %>
            <tr>
                <form action="/admin" method="get">
                    <input type="hidden" readonly name="id" value="<%=purchase.getId()%>">
                    <td><input style="width: 80px;" type="text" readonly value="<%=purchase.getClientLogin()%>" name="login"></td>
                    <td><input style="width: 80px;" type="text" readonly value="<%=purchase.getTotalPrice()%>" name="summary"></td>
                    <td><input style="width: 90px" type="text" readonly value="<%=purchase.getCreateDate()%>" name="createdate"></td>
                    <td><input style="width: 90px" type="text" readonly value="<%=purchase.getCloseDate() == null ? "-" : purchase.getCloseDate()%>" name="closedate"></td>
                    <td><input style="width: 56px" type="text" readonly value="<%=purchase.getStatus()%>" name="status" ></td>
                    <%
                        if (purchase.getStatus().equals("paid")) {
                    %>
                    <td>
                        <input class="btn btn-dark" type="submit" name="act" value="close">
                    </td>
                    <%
                        }
                    %>
                </form>
            </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>
    </div>
</body>
</html>
