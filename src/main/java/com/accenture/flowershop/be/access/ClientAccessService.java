package com.accenture.flowershop.be.access;

import com.accenture.flowershop.be.entity.Client;

public interface ClientAccessService {

    Client get(String login);

    Client add(Client client);

    Client updateBalance(Client client);

}
