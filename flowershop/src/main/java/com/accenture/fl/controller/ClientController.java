package com.accenture.fl.controller;

import com.accenture.bl.entity.Client;
import com.accenture.fl.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.QueryParam;
import java.io.PrintWriter;

//@RestController
//@RequestMapping("/user")
@Controller
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public Client login(@RequestParam("login") String login, @RequestParam("password") String password, HttpServletRequest request) {
        Client client = clientService.login(login, password);
        HttpSession session = request.getSession();
        session.setAttribute("login", client.getLogin());
        session.setAttribute("balance", client.getBalance());
        session.setAttribute("discount", client.getDiscount());
        return client;
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public Client register(@RequestParam("login") String login, @RequestParam("password") String password) {
        return clientService.register(login, password);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
    }

    @RequestMapping(value = "/session", method = RequestMethod.GET)
    @ResponseBody
    public String getSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
//        session.invalidate();
        session.getAttribute("login");
        return  "[{" +
                "        \"login\": " + session.getAttribute("login") +
                "        \"balance\": " + session.getAttribute("balance") +
                "        \"discount\": " + session.getAttribute("discount") +
                "}]";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage() {
        return "final.html";
    }

    @RequestMapping(value = "/flowers", method = RequestMethod.GET)
    public String flowersPage() {
        return "flowers.html";
    }

}
