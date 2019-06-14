package com.accenture.flowershop.fe.servlets;

import com.accenture.flowershop.be.access.FlowerService;
import com.accenture.flowershop.be.entity.Client;
import com.accenture.flowershop.be.entity.Flower;
import com.accenture.flowershop.fe.dto.BasketItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/basket")
public class BasketServlet extends HttpServlet {

    @Autowired
    private FlowerService flowerService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
        view.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
//        view.include(req, resp);
//        resp.sendRedirect("/");

        String name = req.getParameter("name").equals("") ? null : req.getParameter("name");
        double price = req.getParameter("price").equals("") ? 0 : Double.parseDouble(req.getParameter("price"));
//        int quantity = req.getParameter("to").equals("") ? 0 : Double.parseDouble(req.getParameter("to"));
//        int quantitytobuy = req.getParameter("to");
        int quantitytobuy = req.getParameter("quantitytobuy").equals("") ? 0 : Integer.parseInt(req.getParameter("quantitytobuy"));

//        req.setAttribute("name", name);
//        req.setAttribute("from", from);
//        req.setAttribute("to", to);

        HttpSession session = req.getSession();
        Client client =  (Client) session.getAttribute("client");



        // TEST

        List<BasketItem> basketItemList = null;
//                client.getBasketItemList();




        BasketItem basketItem = new BasketItem();
        basketItem.setName(name);
        basketItem.setPrice(price);
        basketItem.setGetQuantitytobuy(quantitytobuy);
        double sum = basketItem.getPrice() * basketItem.getGetQuantitytobuy();
        basketItem.setSum(sum);

        basketItemList.add(basketItem);

        req.setAttribute("basketItemList", basketItemList);

        RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
        view.forward(req, resp);
    }
}
