package com.accenture.flowershop.be.business;

import com.accenture.flowershop.be.entity.Client;

public interface ClientService {

    Client login(String login, String password);

    Client register(Client client);

    Client updateBalance(String login, Double balance);

    Client getByLogin(String login);

}
