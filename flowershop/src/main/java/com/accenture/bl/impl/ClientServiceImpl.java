package com.accenture.bl.impl;

import com.accenture.bl.entity.Client;
import com.accenture.fl.ClientService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Component
@Service
@Repository
@Transactional
public class ClientServiceImpl implements ClientService {

    public ClientServiceImpl(){}

    @PersistenceContext
    private EntityManager em;

    @Override
    public Client login(String login, String password) {
        TypedQuery<Client> q = em.createQuery("SELECT u FROM Client u WHERE u.login = :login AND u.password = :password", Client.class);
        q.setParameter("login", login);
        q.setParameter("password", password);
        return q.getSingleResult();
    }

    @Override
    public Client register(String login, String password) {
        Client client = new Client(login, password);
        em.persist(client);
        return client;
    }
}
