<%@ page import="java.util.List" %>
<%@ page import="com.accenture.flowershop.fe.dto.BasketItemDTO" %>
<%@ page import="com.accenture.flowershop.fe.dto.ClientDTO" %>
<%@ page import="com.accenture.flowershop.fe.dto.FlowerDTO" %>
<%@ page import="com.accenture.flowershop.fe.dto.PurchaseDTO" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Flower Shop</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.7.0/css/all.css' integrity='sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ' crossorigin='anonymous'>
</head>
<%
    ClientDTO client = (ClientDTO) session.getAttribute("client");
    String login = client.getLogin();
    Double balance = client.getBalance();
    Integer discount = client.getDiscount();


//    pageContext.setAttribute("login", client.getLogin());
//    pageContext.setAttribute("balance", client.getBalance());
//    pageContext.setAttribute("discount", client.getDiscount());
%>
<body style="background-color: #999">
    <div class="bg-dark">
        <div class="container d-flex justify-content-between align-items-lg-center">
            <div class="d-flex justify-content-between align-items-lg-center">
                <span><i class="fas fa-seedling" style='font-size:38px;color:green'></i></span>
                <span class="text-white">Flower Shop</span>
            </div>
            <div class="text-white">
                <span class="">Login: <kbd><%=login%></kbd></span>
                <span class="">Balance: <kbd><%=balance%>$</kbd></span>
                <span class="">Discount: <kbd><%=discount%>%</kbd></span>
                <form class="d-inline-block" action="logout" method="post">
                    <input class="btn btn-success btn-sm" type="submit" value="Logout">
                </form>
            </div>
        </div>
    </div>
    <div class="container pt-4 w-50">
        <div class=" pb-1 pt-1 mb-2">
            <form class="m-1" action="/" method="get">
                <div class="d-flex justify-content-between align-items-lg-center">
                    <span><kbd class="">Search</kbd></span>
                    <div class="">
                        <input class="form-control form-control-sm" type="text" name="name" id="name" value="${name}" placeholder="Search...">
                    </div>
                    <span><kbd>From</kbd></span>
                    <div class="">
                        <input class="form-control form-control-sm" type="number" name="from" id="from" value="${from}" placeholder="From Price...">
                    </div>
                    <span><kbd>To</kbd></span>
                    <div class="">
                        <input class="form-control form-control-sm" type="number" name="to" id="to" value="${to}" placeholder="To Price...">
                    </div>
                    <div class="">
                        <input class="btn btn-success btn-sm btn-outline-dark d-inline-block" type="submit" name="act" value="Search">
                    </div>
                </div>
            </form>
        </div>
        <div>
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
                    List<FlowerDTO> flowers = (List<FlowerDTO>) session.getAttribute("flowersList");
                    if (flowers != null) {
                        for (FlowerDTO flower: flowers) {
                %>
                <tr>
                    <form action="/" method="get">
                        <input type="hidden" readonly name="id" value="<%=flower.getId()%>">
                        <td>
                            <kbd><%=flower.getName()%></kbd>
                            <input type="hidden" readonly value="<%=flower.getName()%>" name="name">
                        </td>
                        <td>
                            <kbd><%=flower.getPrice()%></kbd>
                            <input type="hidden" readonly value="<%=flower.getPrice()%>" name="price">
                        </td>
                        <td>
                            <kbd><%=flower.getQuantity()%></kbd>
                            <input type="hidden" readonly value="<%=flower.getQuantity()%>" name="quantity">
                        </td>
                        <td>
                            <kbd>
                                <input class="border-0" style="width: 40px;" type="number" name="quantitytobuy" value="1" min="1" max="<%=flower.getQuantity()%>">
                            </kbd>
                        </td>
                        <td><input class="btn btn-success btn-sm btn-outline-dark" type="submit" name="act" value="+"></td>
                    </form>
                </tr>
                <%
                        }
                    }
                %>
                </tbody>
            </table>
        </div>
        <%
            List<BasketItemDTO> basketItems = (List<BasketItemDTO>) session.getAttribute("basketItemList");
            if (basketItems != null) {
        %>
        <h5>Basket Table</h5>
        <table class="table table-sm">
            <thead class="thead-dark">
                <tr>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Sum</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
            <%
                    for (BasketItemDTO basketItem: basketItems) {
            %>
            <tr>
                <form action="/" method="get">
                    <td>
                        <kbd><%=basketItem.getName()%></kbd>
                        <input type="hidden" readonly value="<%=basketItem.getName()%>" name="name">
                    </td>
                    <td>
                        <kbd><%=basketItem.getPrice()%></kbd>
                        <input type="hidden" readonly value="<%=basketItem.getPrice()%>" name="price">
                    </td>
                    <td>
                        <kbd><%=basketItem.getQuantityToBuy()%></kbd>
                        <input type="hidden" readonly value="<%=basketItem.getQuantityToBuy()%>" name="quantityBuy">
                    </td>
                    <td>
                        <kbd><%=basketItem.getSum()%></kbd>
                        <input type="hidden" readonly value="<%=basketItem.getSum()%>" name="sum">
                    </td>
                    <td><input class="btn btn-danger btn-sm btn-outline-dark" type="submit" name="act" value="x"></td>
                </form>
            </tr>
            <%
                    }
            %>
            </tbody>
        </table>

        <form class="float-right mb-2" action="/" method="get">
            <input class="btn btn-success btn-sm btn-outline-dark" type="submit" name="act" value="Order">
            <%
                Double sum = (Double) session.getAttribute("sum");
//                pageContext.setAttribute("sum", sum);
            %>
            <span><kbd>Sum: <%=sum%></kbd></span>
        </form>
        <h1></h1>
        <%
            }
            List<PurchaseDTO> purchases = (List<PurchaseDTO>) session.getAttribute("purchaseList");
            if (purchases != null) {

        %>
        <h5>Orders Table</h5>
        <table class="table table-sm pt-2">
            <thead class="thead-dark">
            <tr>
                <th scope="col">Summary</th>
                <th scope="col">Created</th>
                <th scope="col">Closed</th>
                <th scope="col">Status</th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <%
                    for (PurchaseDTO purchase: purchases) {
            %>
            <tr>
                <form action="/" method="get">
                    <input type="hidden" readonly name="id" value="<%=purchase.getId()%>">
                    <td>
                        <kbd><%=purchase.getTotalPrice()%></kbd>
                        <input style="width: 80px;" type="hidden" readonly value="<%=purchase.getTotalPrice()%>" name="summary">
                    </td>
                    <td>
                        <kbd><%=purchase.getCreateDate()%></kbd>
                        <input style="width: 90px" type="hidden" readonly value="<%=purchase.getCreateDate()%>" name="createdate">
                    </td>
                    <td>
                        <kbd><%=purchase.getCloseDate() == null ? "-" : purchase.getCloseDate()%></kbd>
                        <input style="width: 90px" type="hidden" readonly value="<%=purchase.getCloseDate() == null ? "-" : purchase.getCloseDate()%>" name="closedate">
                    </td>
                    <td>
                        <kbd><%=purchase.getStatus()%></kbd>
                        <input style="width: 56px" type="hidden" readonly value="<%=purchase.getStatus()%>" name="status" >
                    </td>
                    <%
                        if (purchase.getStatus().equals("created")) {
                    %>
                    <td>
                        <input class="btn btn-success btn-sm btn-outline-dark" type="submit" name="act" value="pay">
                    </td>
                    <%
                        }
                    %>
                </form>
            </tr>
            <%
                    }
            %>
            </tbody>
        </table>
        <%
            }
        %>
<%--            THIS IS FOR REBUILDING CURRENT JSP CODE TO USE JSTL--%>
<%--                IN SOME FUTURE WILL BE IMPLEMENTED--%>

<%--        <%--%>
<%--            List<Purchase> test = (List<Purchase>) session.getAttribute("purchaseList");--%>
<%--            pageContext.setAttribute("testList", test);--%>
<%--        %>--%>
<%--        <table class="table table-sm text-white">--%>
<%--            <thead class="thead-dark">--%>
<%--                <tr>--%>
<%--                    <th>Summary</th>--%>
<%--                    <th>Created</th>--%>
<%--                    <th>Closed</th>--%>
<%--                    <th>Status</th>--%>
<%--                </tr>--%>
<%--            </thead>--%>
<%--            <tbody>--%>
<%--                <c:forEach var="item" items="${testList}" >--%>
<%--                    <tr>--%>
<%--                        <td><c:out value="${item.totalPrice}" /></td>--%>
<%--                        <td><c:out value="${item.createDate}" /></td>--%>
<%--                        <td><c:out value="${item.closeDate}" /></td>--%>
<%--                        <td><c:out value="${item.status}" /></td>--%>
<%--                    </tr>--%>
<%--                </c:forEach>--%>
<%--            </tbody>--%>
<%--        </table>--%>
    </div>
</body>
</html>