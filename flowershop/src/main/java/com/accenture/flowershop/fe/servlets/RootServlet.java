package com.accenture.flowershop.fe.servlets;

import com.accenture.flowershop.be.access.FlowerService;
import com.accenture.flowershop.be.access.PurchaseService;
import com.accenture.flowershop.be.entity.Client;
import com.accenture.flowershop.be.entity.Flower;
import com.accenture.flowershop.be.entity.Purchase;
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
import java.sql.Date;
import java.util.List;

@WebServlet(urlPatterns = "/")
public class RootServlet extends HttpServlet {

    @Autowired
    private FlowerService flowerService;

    @Autowired
    private PurchaseService purchaseService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        if (session.getAttribute("login") != null) {

            List<Flower> flowersList1 = flowerService.get(null,0, 0);
            session.setAttribute("flowersList", flowersList1);

            Client client2 = (Client) session.getAttribute("client");
            List<Purchase> purchaseList = purchaseService.get(client2.getLogin());
            if (purchaseList != null) {
                session.setAttribute("purchaseList", purchaseList);
            }

            String act = req.getParameter("act");
            if (act == null) {
                //no button has been selected
//                System.out.println("НЕ НАЖАЛ КНОПКУ");
            } else if (act.equals("Search")) {
                session = req.getSession();
                //delete button was pressed
//                System.out.println("НАЖАЛ КНОПКУ ПОИСКА");
                String name = req.getParameter("name").equals("") ? null : req.getParameter("name");
                double from = req.getParameter("from").equals("") ? 0 : Double.parseDouble(req.getParameter("from"));
                double to = req.getParameter("to").equals("") ? 0 : Double.parseDouble(req.getParameter("to"));
                req.setAttribute("name", name);
                req.setAttribute("from", from);
                req.setAttribute("to", to);
                List<Flower> flowersList = flowerService.get(name,from, to);
                session.setAttribute("flowersList", flowersList);
                req.setAttribute("flowersList", flowersList);
//                RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
//                view.include(req, resp);
//                resp.sendRedirect("/");
            } else if (act.equals("add")) {
                //update button was pressed
//                System.out.println("ЭТО ЧО ЗА КНОПКА ТАКАЯ");
                String name = req.getParameter("name").equals("") ? null : req.getParameter("name");
                double price = req.getParameter("price").equals("") ? 0 : Double.parseDouble(req.getParameter("price"));
//        int quantity = req.getParameter("to").equals("") ? 0 : Double.parseDouble(req.getParameter("to"));
//        int quantitytobuy = req.getParameter("to");
                int quantitytobuy = req.getParameter("quantitytobuy").equals("") ? 0 : Integer.parseInt(req.getParameter("quantitytobuy"));

//        req.setAttribute("name", name);
//        req.setAttribute("from", from);
//        req.setAttribute("to", to);

                session = req.getSession();
                Client client =  (Client) session.getAttribute("client");



                // TEST

                List<BasketItem> basketItemList = null;
//                        client.getBasketItemList();




                BasketItem basketItem = new BasketItem();
                basketItem.setName(name);
                basketItem.setPrice(price);
                basketItem.setGetQuantitytobuy(quantitytobuy);
                double sum = basketItem.getPrice() * basketItem.getGetQuantitytobuy();
                basketItem.setSum(sum);

                basketItemList.add(basketItem);

                req.setAttribute("basketItemList", basketItemList);
                session.setAttribute("basketItemList", basketItemList);

//                double summary = 0;
                double summary = session.getAttribute("sum") != null ? (double) session.getAttribute("sum") : 0;
//                for (BasketItem bItm : basketItemList) {
                summary += basketItem.getSum();
//                }
                session.setAttribute("sum", summary);


            } else if (act.equals("order")) {
//                HttpSession session1 = req.getSession();
                //someone has altered the HTML and sent a different value!
//                System.out.println("БРЕД КАКОЙ ТО");
//            List<BasketItem> basketItemList = (List<BasketItem>) req.getAttribute("basketItemList");
                List<BasketItem> basketItemList = (List<BasketItem>) session.getAttribute("basketItemList");
//                double summary = 0;
//                for (BasketItem basketItem : basketItemList) {
//                    summary += basketItem.getSum();
//                }
//                req.setAttribute("summary", summary);
                Client client1 =  (Client) session.getAttribute("client");
                Purchase purchase = new Purchase();
                purchase.setClientLogin(client1.getLogin());
                Date date = new Date(System.currentTimeMillis());
                purchase.setCreateDate(date);
                purchase.setStatus("created");
                purchase.setTotalPrice(((Double) session.getAttribute("sum")));


                Purchase purchase1 = purchaseService.add(purchase);


                List<Purchase> purchaseList1 = purchaseService.get(client1.getLogin());

                session.setAttribute("purchaseList", purchaseList1);




                session.removeAttribute("basketItemList");
                session.removeAttribute("sum");
            } else if (act.equals("buy")) {

            }

//            resp.sendRedirect("/WEB-INF/jsp/main.jsp");


            RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
            view.include(req, resp);
        }
        else {
//            resp.sendRedirect("/login");
//            RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
//            view.include(req, resp);
            doPost(req, resp);
        }
    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        resp.sendRedirect("login.jsp");
//        RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
//        view.forward(req, resp);
//        String act = req.getParameter("act");
//        if (act == null) {
//            //no button has been selected
//            System.out.println("НЕ НАЖАЛ КНОПКУ");
//        } else if (act.equals("Search")) {
//            HttpSession session = req.getSession();
//            //delete button was pressed
//            System.out.println("НАЖАЛ КНОПКУ ПОИСКА");
//            String name = req.getParameter("name").equals("") ? null : req.getParameter("name");
//            double from = req.getParameter("from").equals("") ? 0 : Double.parseDouble(req.getParameter("from"));
//            double to = req.getParameter("to").equals("") ? 0 : Double.parseDouble(req.getParameter("to"));
//            req.setAttribute("name", name);
//            req.setAttribute("from", from);
//            req.setAttribute("to", to);
//            List<Flower> flowersList = flowerService.get(name,from, to);
//            session.setAttribute("flowersList", flowersList);
//            req.setAttribute("flowersList", flowersList);
//        } else if (act.equals("basket")) {
//            //update button was pressed
//            System.out.println("ЭТО ЧО ЗА КНОПКА ТАКАЯ");
//            String name = req.getParameter("name").equals("") ? null : req.getParameter("name");
//            double price = req.getParameter("price").equals("") ? 0 : Double.parseDouble(req.getParameter("price"));
////        int quantity = req.getParameter("to").equals("") ? 0 : Double.parseDouble(req.getParameter("to"));
////        int quantitytobuy = req.getParameter("to");
//            int quantitytobuy = req.getParameter("quantitytobuy").equals("") ? 0 : Integer.parseInt(req.getParameter("quantitytobuy"));
//
////        req.setAttribute("name", name);
////        req.setAttribute("from", from);
////        req.setAttribute("to", to);
//
//            HttpSession session = req.getSession();
//            Client client =  (Client) session.getAttribute("client");
//
//            List<BasketItem> basketItemList = client.getBasketItemList();
//            BasketItem basketItem = new BasketItem();
//            basketItem.setName(name);
//            basketItem.setPrice(price);
//            basketItem.setGetQuantitytobuy(quantitytobuy);
//            double sum = basketItem.getPrice() * basketItem.getGetQuantitytobuy();
//            basketItem.setSum(sum);
//
//            basketItemList.add(basketItem);
//
//            req.setAttribute("basketItemList", basketItemList);
//            session.setAttribute("basketItemList", basketItemList);
//        } else if (act.equals("order")) {
//            HttpSession session1 = req.getSession();
//            //someone has altered the HTML and sent a different value!
//            System.out.println("БРЕД КАКОЙ ТО");
////            List<BasketItem> basketItemList = (List<BasketItem>) req.getAttribute("basketItemList");
//            List<BasketItem> basketItemList = (List<BasketItem>) session1.getAttribute("basketItemList");
//            double summary = 0;
//            for (BasketItem basketItem : basketItemList) {
//                summary += basketItem.getSum();
//            }
//            req.setAttribute("summary", summary);
//            Client client1 =  (Client) session1.getAttribute("client");
//            Purchase purchase = new Purchase();
//            purchase.setClientLogin(client1.getLogin());
//            Date date = new Date(System.currentTimeMillis());
//            purchase.setCreateDate(date);
//            purchase.setStatus("created");
//            purchase.setTotalPrice(summary);
//
//
//            Purchase purchase1 = purchaseService.add(purchase);
//
//
//
//        } else if (act.equals("buy")) {
//
//        }

//        RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
//        view.forward(req, resp);
//    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("login");
//        RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
//        view.forward(req, resp);
    }
}
