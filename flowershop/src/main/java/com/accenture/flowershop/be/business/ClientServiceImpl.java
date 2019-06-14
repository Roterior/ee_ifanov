package com.accenture.flowershop.be.business;

import com.accenture.flowershop.be.access.ClientAccessService;
import com.accenture.flowershop.be.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    public ClientServiceImpl(ClientAccessService clientAccessService) {
        this.clientAccessService = clientAccessService;
    }

    private final ClientAccessService clientAccessService;

    @Override
    public Client login(String login, String password) {
        Client client;
        try {
            client = clientAccessService.login(login, password);
        }
        catch (EmptyResultDataAccessException e) {
            System.out.println("РЕАЛЬНО НОЛЬ");
            client = null;
        }
        if (client != null) {
            return client;
        }
        else {
            return null;
        }
    }

    @Override
    public Client register(Client client) {
        Client client1 = clientAccessService.register(client);
        return client1;
    }

    @Override
    public Client updateBalance(String login, double balance) {
        Client client = clientAccessService.get(login);
        client.setBalance(balance);
        Client client1 = clientAccessService.updateBalance(client);
        return client1;
    }

}
