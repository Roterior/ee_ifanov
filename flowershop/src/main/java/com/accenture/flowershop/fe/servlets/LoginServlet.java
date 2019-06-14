package com.accenture.flowershop.fe.servlets;

import com.accenture.flowershop.be.access.ClientAccessService;
import com.accenture.flowershop.be.business.ClientService;
import com.accenture.flowershop.be.entity.Client;
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

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Autowired
    private ClientService clientService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("HI");


//        String login = "admin";
//        String password = "admin123";
//        Client client = clientService.login(login, password);

//        req.getContextPath();
//        doPost(req, resp);
        RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
        view.forward(req, resp);
//        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
//        rd.include(request, response);
//        HttpSession session = request.getSession();
////        if (session.getAttribute("login") != null) {
////            response.sendRedirect("/");
////        }
////        else {
////            response.sendRedirect("login");
////        }
//        try {
//            String login = request.getParameter("login");
//            String pass = request.getParameter("password");
////            boolean submitButtonPressed = request.getParameter("submit") != null;
//            if (clientAccessService.login(login, pass) != null) {
//                Client client = clientAccessService.login(login, pass);
//                session.setAttribute("login", client.getLogin());
//                session.setAttribute("balance", client.getBalance());
//                session.setAttribute("discount", client.getDiscount());
//                System.out.println("ЗАПИСАЛ ЛОГИН В СЕССИЮ");
//                rd.include(request,response);
////                response.sendRedirect("/");
//            }
//            else {
////                out.println("Access denied!");
//                session.setAttribute("errorMessage", "LOGIN DENIED!");
//                System.out.println("STAGE 5");
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }


//        String login = request.getParameter("login");
//        String password = request.getParameter("password");
//
//        request.setAttribute("login", login);
//        request.setAttribute("password", password);
//
//        RequestDispatcher view = request.getRequestDispatcher("main.jsp");
//        view.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
//        view.include(req, resp);

        // Получаем параметры из запроса клиента
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        // Поиск по БД такого клиента

        Client client = null;


        String act = req.getParameter("act");
        if (act == null) {
            //no button has been selected
                System.out.println("НЕ НАЖАЛ КНОПКУ");
        } else if (act.equals("Login")) {
            System.out.println("НАЖАЛ ЛОГИН ВАУ");
            client = clientService.login(login, password);
        }
        // Если клиент нашелся по логину и паролю то
        if (client != null) {
            // Создаем сессию
            HttpSession session = req.getSession();
            session.setAttribute("client", client);
            session.setAttribute("login", client.getLogin());
            session.setAttribute("balance", client.getBalance());
            session.setAttribute("discount", client.getDiscount());
            // Кидаем параметры клиенту
            req.setAttribute("login", client.getLogin());
            req.setAttribute("balance", client.getBalance());
            req.setAttribute("discount", client.getDiscount());
            req.removeAttribute("error");
            // Открываем нужную страницу
            resp.sendRedirect("/");
//            RequestDispatcher view = req.getRequestDispatcher("main.jsp");
//            view.forward(req, resp);
        }
        else {
            System.out.println("ГДЕ МОЯ ЛОГИН СТРАНИНЦА");
            req.setAttribute("error", "Login or Password wrong!");
            resp.sendRedirect("/login");
//            RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
//            view.include(req, resp);
        }
    }
}
