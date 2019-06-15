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
            client = clientAccessService.get(login);
            if (!password.equals(client.getPassword())) {
                return null;
            }
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return client;
    }

    @Override
    public Client register(Client client) {
        Client client1 = null;
        if (client.getLogin() != null && client.getPassword() != null) {
            try {
                client1 = clientAccessService.add(client);
            } catch (Exception e) {
                return null;
            }
        }
        return client1;
    }

    @Override
    public Client updateBalance(String login, double balance) {
        Client client = clientAccessService.get(login);
        client.setBalance(balance);
        return clientAccessService.updateBalance(client);
    }
}
