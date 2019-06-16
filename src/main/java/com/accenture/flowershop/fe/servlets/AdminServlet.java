package com.accenture.flowershop.fe.servlets;

import com.accenture.flowershop.be.business.PurchaseService;
import com.accenture.flowershop.be.entity.Client;
import com.accenture.flowershop.be.entity.Purchase;
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
import java.util.List;

@WebServlet(urlPatterns = "/admin")
public class AdminServlet extends HttpServlet {

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
        if (null != client && client.getLogin().equals("admin")) {




            List<Purchase> purchaseList;
            try {
                purchaseList = purchaseService.getAll();
            } catch (Exception e) {
                purchaseList = null;
            }
            session.setAttribute("purchaseListAll", purchaseList);
            String act = req.getParameter("act");
            if (act != null) {
                if ("close".equals(act)) {
                    int id = req.getParameter("id").equals("") ? 0 : Integer.parseInt(req.getParameter("id"));
                    String login = req.getParameter("login").equals("") ? null : req.getParameter("login");
                    String status = "closed";
                    Purchase purchase = purchaseService.getByIdAndLogin(id, login);
                    if (purchase.getStatus().equals("paid")) {
                        purchaseService.updateStatusClose(id, login, status);
                    }
                    List<Purchase> purchase1 = purchaseService.getAll();
                    session.setAttribute("purchaseListAll", purchase1);
                }
            }
            req.getRequestDispatcher("/WEB-INF/jsp/admin.jsp").forward(req, resp);





        } else {
            resp.sendRedirect("/login");
        }






    }
}
