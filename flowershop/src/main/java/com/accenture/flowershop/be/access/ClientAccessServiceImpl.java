package com.accenture.flowershop.be.access;

import com.accenture.flowershop.be.entity.Client;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class ClientAccessServiceImpl implements ClientAccessService {

    public ClientAccessServiceImpl() {}

    @PersistenceContext
    private EntityManager em;

//    private Session session;
//
//    public ClientAccessServiceImpl(Session session) {
//        super();
//        this.session = session;
//    }


    @Override
    public Client login(String login, String password) {

//        Query<Client> query = session.createNamedQuery("findByLogin", Client.class);
//        query.setParameter("employeeNo", "001");
//        Client client = query.getSingleResult();
//        return client;

//        Query q =em.createNamedQuery("Client.findALl");
//        Query<Client> q = em.createQuery("select c from Client c where c.login = :login");
//        q.setParameter("login", login);
//        return  (Client) q.getResultList().get(0);

        TypedQuery<Client> q = em.createQuery("SELECT c FROM Client c WHERE c.login = :login AND c.password = :password", Client.class);
//        em.createQuery("select b FROM Client b", Client.class).getSingleResult();
        q.setParameter("login", login);
        q.setParameter("password", password);
//        List<Client>
//        if (q.) System.out.println("NULL");
//        else System.out.println("SHIIIIIT");
        return q.getSingleResult();
    }

    @Override
    public Client register(Client client) {
        em.persist(client);
        return client;
    }

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
