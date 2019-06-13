package com.accenture.flowershop.be.access;

import com.accenture.flowershop.be.entity.Client;

public interface ClientService {

    Client login(String login, String password);

    Client register(String login, String password);

    Client updateBalance(String login, double balance);

}
