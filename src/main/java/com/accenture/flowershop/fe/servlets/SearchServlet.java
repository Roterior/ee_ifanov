package com.accenture.flowershop.fe.servlets;

import com.accenture.flowershop.be.access.FlowerService;
import com.accenture.flowershop.be.entity.Flower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/search")
public class SearchServlet extends HttpServlet {

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
        view.include(req, resp);
//        resp.sendRedirect("/");

//        String name = req.getParameter("name").equals("") ? null : req.getParameter("name");
//        double from = req.getParameter("from").equals("") ? 0 : Double.parseDouble(req.getParameter("from"));
//        double to = req.getParameter("to").equals("") ? 0 : Double.parseDouble(req.getParameter("to"));
//        req.setAttribute("name", name);
//        req.setAttribute("from", from);
//        req.setAttribute("to", to);
//        List<Flower> flowersList = flowerService.get(name,from, to);
//        req.setAttribute("flowersList", flowersList);
//        RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
//        view.include(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String name = req.getParameter("name").equals("") ? null : req.getParameter("name");
//        Double from = Double.parseDouble(req.getParameter("from"));
//        Double to = Double.parseDouble(req.getParameter("to"));
//        double from = 0, to = 0;

//        List<Flower> flowersList = flowerService.get(name,from, to);
//        req.setAttribute("flowersList", flowersList);

//        resp.sendRedirect("/");
//        RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
//        view.include(req, resp);
//        doGet(req, resp);

        String name = req.getParameter("name").equals("") ? null : req.getParameter("name");
        double from = req.getParameter("from").equals("") ? 0 : Double.parseDouble(req.getParameter("from"));
        double to = req.getParameter("to").equals("") ? 0 : Double.parseDouble(req.getParameter("to"));
        req.setAttribute("name", name);
        req.setAttribute("from", from);
        req.setAttribute("to", to);
        List<Flower> flowersList = flowerService.get(name,from, to);
        req.setAttribute("flowersList", flowersList);
        RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
        view.forward(req, resp);
    }
}
