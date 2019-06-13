<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Flower Shop</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<%
    String login = (String) request.getSession().getAttribute("login");
    double balance = (Double) request.getSession().getAttribute("balance");
    int discount = (Integer) request.getSession().getAttribute("discount");
%>
<body style="background-color: #777">
    <div class="container">
        <h2>Flower Shop page!</h2>
        <span class="h5">Login: <kbd>${login}</kbd></span>
        <span class="h5">Balance: <kbd>${balance}$</kbd></span>
        <span class="h5">Discount: <kbd>${discount}%</kbd></span>
        <form class="d-inline-block" action="logout" method="post">
            <input class="btn btn-dark" type="submit" value="Logout">
        </form>
    </div>
<%--    <table class="table-striped">--%>
<%--        <tr>--%>
<%--            <td>Name</td>--%>
<%--            <td>Price</td>--%>
<%--            <td>Quanitity</td>--%>
<%--        </tr>--%>
<%--    </table>--%>
<%----%>
<%----%>

<%--//        String password = request.getAttribute("password").toString();--%>
<%----%>
<%----%>
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
<%----%>
<%--%>--%>
<%--    <span>WHAT IS THIS</span>--%>

</body>
</html>
