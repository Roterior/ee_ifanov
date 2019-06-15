package com.accenture.flowershop.be.access;

import com.accenture.flowershop.be.entity.Purchase;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PurchaseAccessServiceImpl implements PurchaseAccessService {

    public PurchaseAccessServiceImpl() {}

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Purchase add(Purchase purchase) {
        em.persist(purchase);
        return purchase;
    }

    @Override
    public List<Purchase> get(String login) {
        TypedQuery<Purchase> q = em.createQuery("SELECT p FROM Purchase p WHERE p.clientLogin = :clientLogin", Purchase.class);
        q.setParameter("clientLogin", login);
        return q.getResultList();
    }

    @Override
    public Purchase getByIdAndLogin(int id, String login) {
        TypedQuery<Purchase> q = em.createQuery("SELECT p FROM Purchase p WHERE p.id = :id AND p.clientLogin = :clientLogin", Purchase.class);
        q.setParameter("id", id);
        q.setParameter("clientLogin", login);
        return q.getSingleResult();
    }

    @Transactional
    @Override
    public Purchase update(Purchase purchase) {
        em.merge(purchase);
        return purchase;
    }
}
