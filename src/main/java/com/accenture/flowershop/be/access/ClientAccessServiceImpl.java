package com.accenture.flowershop.be.access;

import com.accenture.flowershop.be.entity.Client;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class ClientAccessServiceImpl implements ClientAccessService {

    public ClientAccessServiceImpl() {}

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Client add(Client client) {
        em.persist(client);
        return client;
    }

    @Transactional
    @Override
    public Client updateBalance(Client client) {
        em.merge(client);
        return client;
    }

    @Override
    public Client get(String login) {
        TypedQuery<Client> q = em.createQuery("SELECT c FROM Client c WHERE c.login = :login", Client.class);
        q.setParameter("login", login);
        return q.getSingleResult();
    }
}
