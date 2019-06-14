package com.accenture.flowershop.be.business;

import com.accenture.flowershop.be.access.PurchaseService;
import com.accenture.flowershop.be.entity.Purchase;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.accenture.flowershop.app.DBCPDataSource.getConnection;

@Component
@Repository
@Transactional
public class PurchaseServiceImpl implements PurchaseService {

    public PurchaseServiceImpl(){}

//    @PersistenceContext
//    private EntityManager em;

    private Connection con;
    private PreparedStatement pStmt;
    private ResultSet rs;
    private Statement stmt;

    @Override
    public Purchase add(Purchase purchase) {
//        Purchase purchase = new Purchase();
//        purchase.setClientLogin(login);
//        purchase.setCreateDate(createDate);
//        purchase.setTotalPrice(price);
//        purchase.setStatus(status);
//        em.persist(purchase);
//        return purchase;

        Purchase purchase1 = null;
//        if (purchase.getLogin() != null && purchase.getPassword() != null) {
            try {
                con = getConnection();
                pStmt = con.prepareStatement("INSERT INTO PURCHASE (CLIENTLOGIN, CREATEDATE, STATUS, totalPrice) VALUES (?,?,?,?)");
                pStmt.setString(1, purchase.getClientLogin());
                pStmt.setDate(2, purchase.getCreateDate());
                pStmt.setString(3, purchase.getStatus());
                pStmt.setDouble(4, purchase.getTotalPrice());
                pStmt.executeUpdate();
                purchase1 = purchase;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (con != null) con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (pStmt != null) pStmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//        }
        return purchase1;








//        return null;
    }

    @Override
    public List<Purchase> get(String clientLogin) {
//        String query = "SELECT p FROM Purchase p WHERE ";
//        if (clientLogin != null) query += " p.clientLogin = :clientLogin AND ";
//        if (clientLogin == null) query = "SELECT p FROM Purchase p";
//        else query = query.substring(0, query.length() - 4);
//
//        TypedQuery<Purchase> q = em.createQuery(query, Purchase.class);
//        if (clientLogin != null) q.setParameter("clientLogin", clientLogin);
//        return q.getResultList();

        Integer id = null;
        Date closeDate = null;
        Date createDate = null;
        String status = null;
        Double totalPrice = null;
        List<Purchase> purchaseList = null;
        try {
            con = getConnection();
            pStmt = con.prepareStatement("SELECT * FROM PURCHASE WHERE CLIENTLOGIN = ?");
            pStmt.setString(1, clientLogin);
            rs = pStmt.executeQuery();
            purchaseList = new ArrayList<>();
            while (rs.next()) {
                id = rs.getInt("ID");
                closeDate = rs.getDate("CLOSEDATE");
                createDate = rs.getDate("CREATEDATE");
                status = rs.getString("STATUS");
                totalPrice = rs.getDouble("TOTALPRICE");
                purchaseList.add(new Purchase(id, clientLogin, totalPrice, createDate, closeDate, status));
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
        return purchaseList;
    }



    @Override
    public Purchase updateStatus(int id, String clientLogin, Date closeDate) {
//        TypedQuery<Purchase> q = em.createQuery("SELECT p FROM Purchase p WHERE p.id = :id AND p.clientLogin = :clientLogin", Purchase.class);
//        q.setParameter("id", id);
//        q.setParameter("clientLogin", clientLogin);
//        Purchase purchase = q.getSingleResult();
//        purchase.setStatus("paid");
//        purchase.setCloseDate(closeDate);
//        em.merge(purchase);
//        return purchase;


        Integer tempId = null;
        Date tempCloseDate = null;
        Date createDate = null;
        String status = null;
        Double totalPrice = null;
        List<Purchase> purchaseList = null;

        Purchase purchase1 = null;
        try {
            con = getConnection();
            pStmt = con.prepareStatement("UPDATE PURCHASE SET STATUS = ? WHERE CLIENTLOGIN = ?");
            pStmt.setInt(1, id);
            pStmt.setString(2, clientLogin);
            pStmt.executeUpdate();


            pStmt = con.prepareStatement("SELECT * FROM PURCHASE WHERE CLIENTLOGIN = ? AND ID = ?");
            pStmt.setString(1, clientLogin);
            pStmt.setInt(1, id);
            rs = pStmt.executeQuery();
//            purchase1 = new ArrayList<>();
            while (rs.next()) {
                id = rs.getInt("ID");
                closeDate = rs.getDate("CLOSEDATE");
                createDate = rs.getDate("CREATEDATE");
                status = rs.getString("STATUS");
                totalPrice = rs.getDouble("TOTALPRICE");
//                purchaseList.add(new Purchase(id, clientLogin, totalPrice, createDate, closeDate, status));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (pStmt != null) pStmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        }
        return purchase1;




//        return null;
    }

}
