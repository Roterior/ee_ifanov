<%@ page import="java.util.List" %>
<%@ page import="com.accenture.flowershop.fe.dto.PurchaseDTO" %>
<%@ page contentType="text/html;charset=UTF-8" %>
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
        <div class="container d-flex justify-content-between align-items-lg-center">
            <div class="d-flex justify-content-between align-items-lg-center">
                <span><i class="fas fa-seedling" style='font-size:38px;color:green'></i></span>
                <span class="text-white">Flower Shop</span>
            </div>
            <div class="text-white">
                <form class="d-inline-block" action="logout" method="post">
                    <input class="btn btn-success btn-sm" type="submit" value="Logout">
                </form>
            </div>
        </div>
    </div>
    <div class="container pt-3 w-50">
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
                List<PurchaseDTO> purchases = (List<PurchaseDTO>) session.getAttribute("purchaseListAll");
                if (purchases != null) {
                    for (PurchaseDTO purchase: purchases) {
            %>
            <tr>
                <form action="/admin" method="get">
                    <input type="hidden" readonly name="id" value="<%=purchase.getId()%>">
                    <td>
                        <h5 class="font-weight-bolder"><kbd><%=purchase.getClientLogin()%></kbd></h5>
                        <input style="width: 80px;" type="hidden" readonly value="<%=purchase.getClientLogin()%>" name="login">
                    </td>
                    <td>
                        <h5><kbd><%=purchase.getTotalPrice()%></kbd></h5>
                        <input style="width: 80px;" type="hidden" readonly value="<%=purchase.getTotalPrice()%>" name="summary">
                    </td>
                    <td>
                        <h5><kbd><%=purchase.getCreateDate()%></kbd></h5>
                        <input style="width: 90px" type="hidden" readonly value="<%=purchase.getCreateDate()%>" name="createdate">
                    </td>
                    <td>
                        <h5><kbd><%=purchase.getCloseDate() == null ? "-" : purchase.getCloseDate()%></kbd></h5>
                        <input style="width: 90px" type="hidden" readonly value="<%=purchase.getCloseDate() == null ? "-" : purchase.getCloseDate()%>" name="closedate">
                    </td>
                    <td>
                        <h5><kbd><%=purchase.getStatus()%></kbd></h5>
                        <input style="width: 56px" type="hidden" readonly value="<%=purchase.getStatus()%>" name="status" >
                    </td>
                    <%
                        if (purchase.getStatus().equals("paid")) {
                    %>
                    <td>
                        <input class="btn btn-success btn-sm btn-outline-dark" type="submit" name="act" value="close">
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
