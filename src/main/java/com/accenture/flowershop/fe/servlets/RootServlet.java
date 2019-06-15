package com.accenture.flowershop.fe.servlets;

import com.accenture.flowershop.be.business.ClientService;
import com.accenture.flowershop.be.business.FlowerService;
import com.accenture.flowershop.be.business.PurchaseService;
import com.accenture.flowershop.be.entity.Client;
import com.accenture.flowershop.be.entity.Flower;
import com.accenture.flowershop.be.entity.Purchase;
import com.accenture.flowershop.fe.dto.BasketItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/")
public class RootServlet extends HttpServlet {

    @Autowired
    private ClientService clientService;

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
        Client client = (Client) session.getAttribute("client");
        if (null != client) {
            List<Flower> flowersList = flowerService.getByNameAndPrice(null,0, 0);
            session.setAttribute("flowersList", flowersList);
            List<Purchase> purchaseList = purchaseService.getByLogin(client.getLogin());
            if (purchaseList != null) {
                session.setAttribute("purchaseList", purchaseList);
            }
            String act = req.getParameter("act");
            if (act != null) {
                switch (act) {
                    case "Search": {
                        String searchName = req.getParameter("name").equals("") ? null : req.getParameter("name");
                        double from = req.getParameter("from").equals("") ? 0 : Double.parseDouble(req.getParameter("from"));
                        double to = req.getParameter("to").equals("") ? 0 : Double.parseDouble(req.getParameter("to"));
                        session.setAttribute("name", searchName);
                        session.setAttribute("from", from);
                        session.setAttribute("to", to);
                        List<Flower> flowersList1 = flowerService.getByNameAndPrice(searchName, from, to);
                        session.setAttribute("flowersList", flowersList1);
                        break;
                    }
                    case "add": {
                        String name = req.getParameter("name").equals("") ? null : req.getParameter("name");
                        double price = req.getParameter("price").equals("") ? 0 : Double.parseDouble(req.getParameter("price"));
                        int quantity = req.getParameter("quantity").equals("") ? 0 : Integer.parseInt(req.getParameter("quantity"));
                        int quantitytobuy = req.getParameter("quantitytobuy").equals("") ? 0 : Integer.parseInt(req.getParameter("quantitytobuy"));
                        int finalQuantity = quantity - quantitytobuy;
                        if (quantitytobuy <= quantity && quantitytobuy > 0) {
                            flowerService.updateQuantity(name, finalQuantity);
                            List<BasketItem> basketItemList = new ArrayList<>();
                            if (session.getAttribute("basketItemList") != null) {
                                basketItemList = (List<BasketItem>) session.getAttribute("basketItemList");
                            }
                            BasketItem basketItem = new BasketItem();
                            basketItem.setName(name);
                            basketItem.setPrice(price);
                            basketItem.setGetQuantitytobuy(quantitytobuy);
                            double sum = basketItem.getPrice() * basketItem.getGetQuantitytobuy();
                            basketItem.setSum(sum);
                            basketItemList.add(basketItem);
                            session.setAttribute("basketItemList", basketItemList);
                            double summary = session.getAttribute("sum") != null ? (double) session.getAttribute("sum") : 0;
                            summary += basketItem.getSum() - ((basketItem.getSum() * (client.getDiscount())) / 100);
                            session.setAttribute("sum", summary);
                        }
                        break;
                    }
                    case "order": {
                        List<BasketItem> basketItemList = (List<BasketItem>) session.getAttribute("basketItemList");
                        Purchase purchase = new Purchase();
                        purchase.setClientLogin(client.getLogin());
                        Date date = new Date(System.currentTimeMillis());
                        purchase.setCreateDate(date);
                        purchase.setStatus("created");
                        double sum = 0;
                        if (session.getAttribute("sum") != null) {
                            purchase.setTotalPrice(((Double) session.getAttribute("sum")));
                            sum = (double) session.getAttribute("sum");
                        }
                        if (sum > 1) {

//                            Purchase purchase1 = new Purchase();

//                            List<Flower> flowerList = purchase.getFlowerList();
//                            for (BasketItem basketItem : basketItemList) {
//                                Flower flower = new Flower();
//                                flower.setName(basketItem.getName());
//                                flower.setPrice(basketItem.getPrice());
//                                flower.setQuantity(basketItem.getQuantity());
////                                flowerList.add(flower);
//                            }
//                            purchase.setFlowerList(flowerList);
                            Purchase purchase1 = purchaseService.add(purchase);

//                            purchase1.setFlowerList();
                            List<Purchase> purchaseList1 = purchaseService.getByLogin(client.getLogin());
                            session.setAttribute("purchaseList", purchaseList1);

                            session.removeAttribute("basketItemList");
                            session.removeAttribute("sum");
                        }
                        break;
                    }
                    case "buy": {
                        int id = req.getParameter("id").equals("") ? 0 : Integer.parseInt(req.getParameter("id"));
                        double summary = req.getParameter("summary").equals("") ? 0 : Double.parseDouble(req.getParameter("summary"));
                        String status = "paid";
                        Date closeDate = new Date(System.currentTimeMillis());
                        Purchase purchase = purchaseService.getByIdAndLogin(id, client.getLogin());
                        if (purchase.getStatus().equals("created")) {
                            if (client.getBalance() >= summary) {
                                purchaseService.updateStatus(id, client.getLogin(), closeDate, status);
                                purchaseList = purchaseService.getByLogin(client.getLogin());
                                if (purchaseList != null) {
                                    session.setAttribute("purchaseList", purchaseList);
                                }

                                double finalBalance = client.getBalance() - summary;
                                clientService.updateBalance(client.getLogin(), finalBalance);

                                client.setBalance(finalBalance);
                                session.setAttribute("client", client);

                            }
                        }
                        break;
                    }
                }
            }
            req.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(req, resp);
        }
        else {
            resp.sendRedirect("/login");
        }
    }
}
