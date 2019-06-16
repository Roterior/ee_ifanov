package com.accenture.flowershop.fe.servlets;

import com.accenture.flowershop.be.business.ClientService;
import com.accenture.flowershop.be.entity.Client;
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

@WebServlet(urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {

    @Autowired
    private ClientService clientService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Client client = (Client) session.getAttribute("client");
        if (null == client) {
            req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login").equals("") ? null : req.getParameter("login");
        String password = req.getParameter("password").equals("") ? null : req.getParameter("password");
        String fname = req.getParameter("fname").equals("") ? null : req.getParameter("fname");
        String lname = req.getParameter("lname").equals("") ? null : req.getParameter("lname");
        String mname = req.getParameter("mname").equals("") ? null : req.getParameter("mname");
        String phoneNumber = req.getParameter("phonenumber").equals("") ? null : req.getParameter("phonenumber");
        String address = req.getParameter("address").equals("") ? null : req.getParameter("address");
        Client client = clientService.register(new Client(login, password, lname, fname, mname, address, phoneNumber));
        if (client != null) {
            req.removeAttribute("error2");
            resp.sendRedirect("/login");
        }
        else {
            req.setAttribute("error2", "Something went wrong!");
            req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
        }
    }
}
