package com.accenture.flowershop.be.business;

import com.accenture.flowershop.be.access.FlowerService;
import com.accenture.flowershop.be.entity.Flower;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.accenture.flowershop.app.DBCPDataSource.getConnection;

@Component
@Repository
@Transactional
public class FlowerServiceImpl implements FlowerService {

    public FlowerServiceImpl(){}

//    @PersistenceContext
//    private EntityManager em;

    private Connection con;
    private Statement stmt;
    private PreparedStatement pStmt;
    private ResultSet rs;

    @Override
    public Flower findByName(String name) {
//        TypedQuery<Flower> q = em.createQuery("SELECT u FROM Flower u WHERE UPPER(u.name) = UPPER(:name)", Flower.class);
//        q.setParameter("name", name);
//        return q.getSingleResult();
        return null;
    }

    @Override
    public List<Flower> get(String name, double from, double to) {
        String tempName;
        double tempPrice;
        int tempQuantity;
        List<Flower>flowerList = null;
        try {
            con = getConnection();
            String query = "SELECT * FROM FLOWER WHERE ";
            if (name != null) query += " UPPER(NAME) LIKE UPPER('%" + name + "%') AND ";
            if (to != 0) query += " PRICE <= " + to + " AND ";
            if (from != 0) query += " PRICE >= " + from + " AND ";
            if (to == 0 && from == 0 && name == null) query = "SELECT * FROM FLOWER";
            else query = query.substring(0, query.length() - 4);
            query += " ORDER BY PRICE ASC";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            flowerList = new ArrayList<>();
            while (rs.next()) {
                tempName = rs.getString("NAME");
                tempPrice = rs.getDouble("PRICE");
                tempQuantity = rs.getInt("QUANTITY");
                flowerList.add(new Flower(tempName, tempPrice, tempQuantity));
            }
        }
        catch (Exception e) { e.printStackTrace(); }
        finally {
            try { if (con != null) con.close(); }
            catch (Exception e) { e.printStackTrace(); }
            try { if (pStmt != null) pStmt.close(); }
            catch (Exception e) { e.printStackTrace(); }
            try { if (rs != null) rs.close(); }
            catch (Exception e) { e.printStackTrace(); }
        }
        return flowerList;

//        String query = "SELECT m FROM Flower m WHERE ";
//        if (name != null) query += " UPPER(m.name) LIKE UPPER(:name) AND ";
//        if (to != 0) query += " m.price <= :toRating AND ";
//        if (from != 0) query += " m.price >= :fromRating AND ";
//        if (to == 0 && from == 0 && name == null) query = "SELECT m FROM Flower m";
//        else query = query.substring(0, query.length() - 4);
//        query += " ORDER BY m.price ASC";
//        TypedQuery<Flower> q = em.createQuery(query, Flower.class);
//        if (name != null) q.setParameter("name", "%" + name + "%");
//        if (to != 0) q.setParameter("toRating", to);
//        if (from != 0) q.setParameter("fromRating", from);
//        return q.getResultList();

//        return null;
    }

    @Override
    public Flower updateQuantity(String name, int quantity) {
//        TypedQuery<Flower> q = em.createQuery("SELECT u FROM Flower u WHERE UPPER(u.name) = UPPER(:name)", Flower.class);
//        q.setParameter("name", name);
//        Flower flower = q.getSingleResult();
//        flower.setQuantity(quantity);
//        em.merge(flower);
//        return flower;
        return null;
    }

}
