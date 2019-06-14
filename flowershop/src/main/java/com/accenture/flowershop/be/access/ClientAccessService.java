package com.accenture.flowershop.be.access;

import com.accenture.flowershop.be.entity.Client;

public interface ClientAccessService {

    Client login(String login, String password);

    Client register(Client client);

    Client updateBalance(Client client);

    Client get(String login);
}
