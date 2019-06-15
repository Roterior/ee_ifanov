<%@ page import="java.util.*" %>
<%@ page import="com.accenture.flowershop.be.entity.Flower" %>
<%@ page import="com.accenture.flowershop.fe.dto.BasketItem" %>
<%@ page import="com.accenture.flowershop.be.entity.Purchase" %>
<%@ page import="com.accenture.flowershop.be.entity.Client" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Flower Shop</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.7.0/css/all.css' integrity='sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ' crossorigin='anonymous'>
</head>
<%
    Client client = (Client) request.getSession().getAttribute("client");
    pageContext.setAttribute("login", client.getLogin());
    pageContext.setAttribute("balance", client.getBalance());
    pageContext.setAttribute("discount", client.getDiscount());
%>
<body style="background-color: #999">
    <div class="bg-dark">
        <div class="container">
            <i class='fas fa-seedling' style='font-size:48px;color:white'></i>
            <h3 class="text-white d-inline-block">Flower Shop</h3>
        </div>
    </div>
    <div class="container pt-1 w-50">

        <span class="h5">Login: <kbd>${login}</kbd></span>
        <span class="h5">Balance: <kbd>${balance}$</kbd></span>
        <span class="h5">Discount: <kbd>${discount}%</kbd></span>
        <form class="d-inline-block" action="logout" method="post">
            <input class="btn btn-dark" type="submit" value="Logout">
        </form>
    </div>
    <div class="container pt-1 w-50">
        <div>
            <form action="/" method="get">
                <div class="row">
                    <div class="form-group col-sm">
<%--                        <label class="h5 font-weight-bold" for="name">Search:</label>--%>
                        <input class="form-control" type="text" name="name" id="name" value="${name}" placeholder="Search...">
                    </div>
                    <div class="form-group col-sm">
<%--                        <label class="h5 font-weight-bold" for="from">From:</label>--%>
                        <input class="form-control" type="number" name="from" id="from" value="${from}" placeholder="From Price...">
                    </div>
                    <div class="form-group col-sm">
<%--                        <label class="h5 font-weight-bold" for="to">To:</label>--%>
                        <input class="form-control" type="number" name="to" id="to" value="${to}" placeholder="To Price...">
                    </div>
                    <input class="btn btn-dark h-25" type="submit" name="act" value="Search">
                </div>
            </form>
        </div>
        <table class="table table-sm">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Price</th>
                    <th scope="col">Quantity</th>
                    <th>To Buy</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
            <%
                List<Flower> flowers = (List<Flower>) session.getAttribute("flowersList");
                if (flowers != null)
                    for (Flower flower: flowers) {
            %>
            <tr>
                <form action="/" method="get">
                    <td><input type="text" readonly value="<%=flower.getName()%>" name="name"></td>
                    <td><input style="width: 60px" type="text" readonly value="<%=flower.getPrice()%>" name="price"></td>
                    <td><input style="width: 60px" type="text" readonly value="<%=flower.getQuantity()%>" name="quantity"></td>
                    <td><input style="width: 50px" type="number" name="quantitytobuy" value="1" min="1"></td>
                    <td><input class="btn btn-dark" type="submit" name="act" value="add"></td>
                </form>
            </tr>
            <%}%>
            </tbody>
        </table>
        <span>Basket Table</span>
        <table class="table table-sm">
            <thead class="thead-dark">
                <tr>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Sum</th>
                </tr>
            </thead>
            <tbody>
            <%
                List<BasketItem> basketItems = (List<BasketItem>) session.getAttribute("basketItemList");
                if (basketItems != null)
                    for (BasketItem basketItem: basketItems) {
            %>
            <tr>
                <form action="basket" method="post" onclick="<%  %>">
                    <td><%=basketItem.getName()%></td>
                    <td><%=basketItem.getPrice()%></td>
                    <td><%=basketItem.getGetQuantitytobuy()%></td>
                    <td><%=basketItem.getSum()%></td>
                </form>
            </tr>
            <%}%>
            </tbody>
        </table>
        <form action="/" method="get">
            <input class="btn btn-dark" type="submit" name="act" value="order">
            <%
                Double sum = (Double) session.getAttribute("sum");
            %>
            <span>Summ: ${sum}</span>
        </form>

        <h1></h1>
        <table class="table table-sm pt-2">
            <thead class="thead-dark">
            <tr>
                <th>Summ</th>
                <th>Create Date</th>
                <th>Close Date</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>
            <%
//                ArrayList<Purchase> purchases = request.getAttribute("purchaseList") != null ? (ArrayList<Purchase>) request.getAttribute("purchaseList") : null;
                List<Purchase> purchases = (List<Purchase>) session.getAttribute("purchaseList");
                if (purchases != null)
                    for (Purchase purchase: purchases) {
            %>
            <tr>
                <form action="/" method="post" onclick="<%  %>">
                    <td><input style="width: 80px;" type="text" readonly value="<%=purchase.getTotalPrice()%>" name="summary"></td>
                    <td><input style="width: 90px" type="text" readonly value="<%=purchase.getCreateDate()%>" name="createdate"></td>
                    <td><input style="width: 90px" type="text" readonly value="<%=purchase.getCloseDate()%>" name="closedate"></td>
                    <td><input style="width: 56px" type="text" readonly value="<%=purchase.getStatus()%>" name="status" ></td>
                    <td>
                        <%
                            if (purchase.getStatus().equals("created")) {
                        %>
                        <input class="btn btn-dark" type="submit" name="act" value="buy">
                        <%
                            }
                        %>


                    </td>
                </form>
            </tr>
            <%}%>
            </tbody>
        </table>
    </div>











<%--//        if (login != null) {--%>
<%--//            out.println(login + "<br>We have <br><br>");--%>
<%--//            out.println(password + "<br>yes <br><br>");--%>
<%--//        }--%>
<%--//        Iterator it = result.iterator();--%>
<%--//        out.println("<br>We have <br><br>");--%>
<%--//        while(it.hasNext()){--%>
<%--//            out.println(it.next()+"<br>");--%>
<%--//        }--%>
<%--    %>--%>
</body>
</html>
