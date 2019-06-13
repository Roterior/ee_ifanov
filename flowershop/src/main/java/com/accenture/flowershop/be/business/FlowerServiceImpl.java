package com.accenture.flowershop.be.business;

import com.accenture.flowershop.be.access.FlowerService;
import com.accenture.flowershop.be.entity.Flower;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
@Repository
@Transactional
public class FlowerServiceImpl implements FlowerService {

    public FlowerServiceImpl(){}

    @PersistenceContext
    private EntityManager em;

    @Override
    public Flower findByName(String name) {
        TypedQuery<Flower> q = em.createQuery("SELECT u FROM Flower u WHERE UPPER(u.name) = UPPER(:name)", Flower.class);
        q.setParameter("name", name);
        return q.getSingleResult();
    }

    @Override
    public List<Flower> get(String name, double from, double to) {
        String query = "SELECT m FROM Flower m WHERE ";
        if (name != null) query += " UPPER(m.name) LIKE UPPER(:name) AND ";
        if (to != 0) query += " m.price <= :toRating AND ";
        if (from != 0) query += " m.price >= :fromRating AND ";
        if (to == 0 && from == 0 && name == null) query = "SELECT m FROM Flower m";
        else query = query.substring(0, query.length() - 4);
        query += " ORDER BY m.price ASC";
        TypedQuery<Flower> q = em.createQuery(query, Flower.class);
        if (name != null) q.setParameter("name", "%" + name + "%");
        if (to != 0) q.setParameter("toRating", to);
        if (from != 0) q.setParameter("fromRating", from);
        return q.getResultList();
    }

    @Override
    public Flower updateQuantity(String name, int quantity) {
        TypedQuery<Flower> q = em.createQuery("SELECT u FROM Flower u WHERE UPPER(u.name) = UPPER(:name)", Flower.class);
        q.setParameter("name", name);
        Flower flower = q.getSingleResult();
        flower.setQuantity(quantity);
        em.merge(flower);
        return flower;
    }

}
