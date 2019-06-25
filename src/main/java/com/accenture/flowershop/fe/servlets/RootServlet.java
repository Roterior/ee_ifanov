package com.accenture.flowershop.fe.servlets;

import com.accenture.flowershop.be.business.ClientService;
import com.accenture.flowershop.be.business.FlowerService;
import com.accenture.flowershop.be.business.PurchaseService;
import com.accenture.flowershop.be.entity.Client;
import com.accenture.flowershop.be.entity.Flower;
import com.accenture.flowershop.be.entity.FlowerFilter;
import com.accenture.flowershop.be.entity.Purchase;
import com.accenture.flowershop.fe.dto.BasketItemDTO;
import com.accenture.flowershop.fe.dto.ClientDTO;
import com.accenture.flowershop.fe.dto.FlowerDTO;
import com.accenture.flowershop.fe.dto.PurchaseDTO;
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
        ClientDTO client = (ClientDTO) session.getAttribute("client");

        if (client == null) {
            resp.sendRedirect("/login");
        }
        else {
            String act = req.getParameter("act") != null ? req.getParameter("act") : "";
            switch (act) {
                case "Search": {
                    String searchName = req.getParameter("name").equals("") ? null : req.getParameter("name");
                    double searchFrom = req.getParameter("from").equals("") ? 0 : Double.parseDouble(req.getParameter("from"));
                    double searchTo = req.getParameter("to").equals("") ? 0 : Double.parseDouble(req.getParameter("to"));

                    session.setAttribute("name", searchName);
                    session.setAttribute("from", searchFrom);
                    session.setAttribute("to", searchTo);

                    FlowerFilter flowerFilter = new FlowerFilter();
                    flowerFilter.setName(searchName);
                    flowerFilter.setFromPrice(searchFrom);
                    flowerFilter.setToPrice(searchTo);

                    List<FlowerDTO> flowersList1 = mapToFlowerDTOList(flowerService.getByFilter(flowerFilter));
                    session.setAttribute("flowersList", flowersList1);

                    doPost(req, resp);
                    break;
                }
                case "+": {
                    Long id = req.getParameter("id").equals("") ? null : Long.parseLong(req.getParameter("id"));
                    int quantityToBuy = req.getParameter("quantityToBuy").equals("") ? 0 : Integer.parseInt(req.getParameter("quantityToBuy"));

                    FlowerDTO flowerDTO = mapToFlowerDTO(flowerService.getById(id));

                    if (flowerDTO != null && quantityToBuy <= flowerDTO.getQuantity() && quantityToBuy > 0) {
                        List<BasketItemDTO> basketItemList = new ArrayList<>();
                        if (session.getAttribute("basketItemList") != null) {
                            basketItemList = (List<BasketItemDTO>) session.getAttribute("basketItemList");
                        }

                        boolean isFound = false;
                        for (BasketItemDTO basketItemDTO : basketItemList) {
                            if (basketItemDTO.getName().equals(flowerDTO.getName())) {
                                int tempQuantity = basketItemDTO.getQuantityToBuy() + quantityToBuy;
                                basketItemDTO.setQuantityToBuy(tempQuantity);
                                double sum = basketItemDTO.getPrice() * basketItemDTO.getQuantityToBuy();
                                basketItemDTO.setSum(sum);
                                isFound = true;
                                break;
                            }
                        }

                        if (!isFound) {
                            BasketItemDTO basketItem = new BasketItemDTO();
                            basketItem.setId(id);
                            basketItem.setName(flowerDTO.getName());
                            basketItem.setPrice(flowerDTO.getPrice());
                            basketItem.setQuantityToBuy(quantityToBuy);
                            double sum = basketItem.getPrice() * basketItem.getQuantityToBuy();
                            basketItem.setSum(sum);
                            basketItemList.add(basketItem);
                        }

                        double summary = 0;
                        for (BasketItemDTO basketItemDTO : basketItemList) {
                            summary += basketItemDTO.getSum() - ((basketItemDTO.getSum() * (client.getDiscount())) / 100);
                            session.setAttribute("sum", summary);
                        }

                        session.setAttribute("basketItemList", basketItemList);
                        doPost(req, resp);
                    }
                    break;
                }
                case "x": {
                    Long id = req.getParameter("id").equals("") ? null : Long.parseLong(req.getParameter("id"));

                    List<BasketItemDTO> basketItemList = new ArrayList<>();
                    if (session.getAttribute("basketItemList") != null) {
                        basketItemList = (List<BasketItemDTO>) session.getAttribute("basketItemList");
                    }

                    for (BasketItemDTO basketItemDTO : basketItemList) {
                        if (basketItemDTO.getId().equals(id)) {
                            basketItemList.remove(basketItemDTO);
                            break;
                        }
                    }

                    if (basketItemList.size() == 0) {
                        basketItemList = null;
                    }

                    session.setAttribute("basketItemList", basketItemList);
                    double summary = 0;
                    if (basketItemList != null) {
                        for (BasketItemDTO basketItemDTO : basketItemList) {
                            summary += basketItemDTO.getSum() - ((basketItemDTO.getSum() * (client.getDiscount())) / 100);
                            session.setAttribute("sum", summary);
                        }
                    }
                    else {
                        session.setAttribute("sum", null);
                    }

                    doPost(req, resp);
                    break;
                }
                case "Order": {
                    PurchaseDTO purchase = new PurchaseDTO();
                    purchase.setClientLogin(client.getLogin());

                    double sum = 0;
                    if (session.getAttribute("sum") != null) {
                        sum = (double) session.getAttribute("sum");
                        purchase.setTotalPrice(((Double) session.getAttribute("sum")));
                    }

                    if (sum > 1) {
                        List<BasketItemDTO> basketItemList = (List<BasketItemDTO>) session.getAttribute("basketItemList");
                        for (BasketItemDTO basketItemDTO : basketItemList) {
                            int quantityBuy = basketItemDTO.getQuantityToBuy();
                            FlowerDTO flowerDTO = mapToFlowerDTO(flowerService.getById(basketItemDTO.getId()));
                            if (flowerDTO != null) {
                                int finalQuantity = flowerDTO.getQuantity() - quantityBuy;
                                flowerService.updateQuantity(basketItemDTO.getId(), finalQuantity);
                            }
                        }

                        purchaseService.add(mapToPurchase(purchase));
                        List<PurchaseDTO> purchaseList1 = mapToPurchaseDTOList(purchaseService.getByLogin(client.getLogin()));

                        session.setAttribute("purchaseList", purchaseList1);
                        session.removeAttribute("basketItemList");
                        session.removeAttribute("sum");
                        doPost(req, resp);
                    }
                    break;
                }
                case "pay": {
                    Long id = req.getParameter("id").equals("") ? null : Long.parseLong(req.getParameter("id"));

                    PurchaseDTO purchase = mapToPurchaseDTO(purchaseService.getById(id));
                    if (purchase != null && purchase.getStatus().equals("created")) {
                        if (client.getBalance() >= purchase.getTotalPrice()) {
                            purchaseService.updateCloseDateAndStatus(id);
                            List<PurchaseDTO> purchaseList = mapToPurchaseDTOList(purchaseService.getByLogin(client.getLogin()));

                            if (purchaseList != null) {
                                session.setAttribute("purchaseList", purchaseList);
                            }

                            double finalBalance = client.getBalance() - purchase.getTotalPrice();
                            clientService.updateBalance(client.getLogin(), finalBalance);
                            client.setBalance(finalBalance);

                            session.setAttribute("client", client);
                            doPost(req, resp);
                        }
                    }
                    break;
                }
                default: {
                    ClientDTO clientTemp = mapToClientDTO(clientService.getByLogin(client.getLogin()));
                    session.setAttribute("client", clientTemp);

                    String searchSesName = (String) session.getAttribute("name");
                    double searchSesFrom = session.getAttribute("from") != null ? (Double) session.getAttribute("from") : 0;
                    double searchSesTo = session.getAttribute("to") != null ? (Double) session.getAttribute("to") : 0;

                    FlowerFilter flowerFilter = new FlowerFilter();
                    flowerFilter.setName(searchSesName);
                    flowerFilter.setFromPrice(searchSesFrom);
                    flowerFilter.setToPrice(searchSesTo);

                    List<FlowerDTO> flowersList = mapToFlowerDTOList(flowerService.getByFilter(flowerFilter));
                    if (flowersList != null && flowersList.size() != 0) {
                        session.setAttribute("flowersList", flowersList);
                    }
                    else {
                        session.setAttribute("flowersList", null);
                    }

                    List<PurchaseDTO> purchaseList = mapToPurchaseDTOList(purchaseService.getByLogin(client.getLogin()));
                    if (purchaseList != null && purchaseList.size() != 0) {
                        session.setAttribute("purchaseList", purchaseList);
                    }
                    else {
                        session.setAttribute("purchaseList", null);
                    }
                    req.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(req, resp);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/");
    }

    private ClientDTO mapToClientDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        if (client != null) {
            clientDTO.setLogin(client.getLogin());
            clientDTO.setPassword(client.getPassword());
            clientDTO.setRole(client.getRole());
            clientDTO.setlName(client.getlName());
            clientDTO.setfName(client.getfName());
            clientDTO.setmName(client.getmName());
            clientDTO.setAddress(client.getAddress());
            clientDTO.setPhoneNumber(client.getPhoneNumber());
            clientDTO.setBalance(client.getBalance());
            clientDTO.setDiscount(client.getDiscount());
        }
        else {
            return null;
        }
        return clientDTO;
    }
    
    private FlowerDTO mapToFlowerDTO(Flower flower) {
        FlowerDTO flowerDTO = new FlowerDTO();
        if (flower != null) {
            flowerDTO.setId(flower.getId());
            flowerDTO.setName(flower.getName());
            flowerDTO.setPrice(flower.getPrice());
            flowerDTO.setQuantity(flower.getQuantity());
        }
        else {
            return null;
        }
        return flowerDTO;
    }

    private List<FlowerDTO> mapToFlowerDTOList(List<Flower> flowerList) {
        List<FlowerDTO> flowerDTOList = new ArrayList<>(flowerList.size());
        for (Flower flower : flowerList) {
            flowerDTOList.add(mapToFlowerDTO(flower));
        }
        return flowerDTOList;
    }

    private PurchaseDTO mapToPurchaseDTO(Purchase purchase) {
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        if (purchase != null) {
            purchaseDTO.setId(purchase.getId());
            purchaseDTO.setClientLogin(purchase.getClientLogin());
            purchaseDTO.setTotalPrice(purchase.getTotalPrice());
            purchaseDTO.setCreateDate(purchase.getCreateDate());
            purchaseDTO.setCloseDate(purchase.getCloseDate());
            purchaseDTO.setStatus(purchase.getStatus());
        }
        else {
            return null;
        }
        return purchaseDTO;
    }

    private Purchase mapToPurchase(PurchaseDTO purchaseDTO) {
        Purchase purchase = new Purchase();
        purchase.setId(purchaseDTO.getId());
        purchase.setClientLogin(purchaseDTO.getClientLogin());
        purchase.setTotalPrice(purchaseDTO.getTotalPrice());
        purchase.setCreateDate(purchaseDTO.getCreateDate());
        purchase.setCloseDate(purchaseDTO.getCloseDate());
        purchase.setStatus(purchaseDTO.getStatus());
        return purchase;
    }

    private List<PurchaseDTO> mapToPurchaseDTOList(List<Purchase> purchaseList) {
        List<PurchaseDTO> purchaseDTO = new ArrayList<>(purchaseList.size());
        for (Purchase purchase : purchaseList) {
            purchaseDTO.add(mapToPurchaseDTO(purchase));
        }
        return purchaseDTO;
    }
}
