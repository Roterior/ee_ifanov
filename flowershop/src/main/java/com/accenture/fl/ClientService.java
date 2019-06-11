package com.accenture.fl;

import com.accenture.bl.entity.Client;

public interface ClientService {

    Client login(String login, String password);

    Client register(String login, String password);

}
