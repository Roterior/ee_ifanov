package com.accenture.flowershop.be.business;

import com.accenture.flowershop.be.access.PurchaseService;
import com.accenture.flowershop.be.entity.Purchase;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.util.List;

@Component
@Repository
@Transactional
public class PurchaseServiceImpl implements PurchaseService {

    public PurchaseServiceImpl(){}

    @PersistenceContext
    private EntityManager em;

    @Override
    public Purchase add(String login, Date createDate, double price, String status) {
        Purchase purchase = new Purchase();
        purchase.setClientLogin(login);
        purchase.setCreateDate(createDate);
        purchase.setTotalPrice(price);
        purchase.setStatus(status);
        em.persist(purchase);
        return purchase;
    }

    @Override
    public List<Purchase> get(String clientLogin) {
        String query = "SELECT p FROM Purchase p WHERE ";
        if (clientLogin != null) query += " p.clientLogin = :clientLogin AND ";
        if (clientLogin == null) query = "SELECT p FROM Purchase p";
        else query = query.substring(0, query.length() - 4);

        TypedQuery<Purchase> q = em.createQuery(query, Purchase.class);
        if (clientLogin != null) q.setParameter("clientLogin", clientLogin);
        return q.getResultList();
    }

    @Override
    public Purchase updateStatus(int id, String clientLogin, Date closeDate) {
        TypedQuery<Purchase> q = em.createQuery("SELECT p FROM Purchase p WHERE p.id = :id AND p.clientLogin = :clientLogin", Purchase.class);
        q.setParameter("id", id);
        q.setParameter("clientLogin", clientLogin);
        Purchase purchase = q.getSingleResult();
        purchase.setStatus("paid");
        purchase.setCloseDate(closeDate);
        em.merge(purchase);
        return purchase;
    }

}
