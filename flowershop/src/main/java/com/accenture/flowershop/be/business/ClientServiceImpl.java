package com.accenture.flowershop.be.business;

import com.accenture.flowershop.be.access.ClientService;
import com.accenture.flowershop.be.entity.Client;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
//import javax.persistence.TypedQuery;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;

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
//        TypedQuery<Client> q = em.createQuery("Select c from Client c where c.login = :login and c.password = :password", Client.class);
//        q.setParameter("login", login);
//        q.setParameter("password", password);
//        return q.getSingleResult();

        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        double balance = 0;
        String tempLogin = null;
        String tempPassword = null;
        int discount = 0;
        Client client = null;


        if (login != null && password != null) {
            try {
                Locale.setDefault(Locale.ENGLISH);
                con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");
//                DriverManager.registerDriver(new jdbc.driver.OracleDriver());
                Class.forName ("org.h2.Driver");
                pStmt = con.prepareStatement("SELECT * FROM CLIENT WHERE LOGIN = ? AND PASSWORD = ?");
                pStmt.setString(1, login);
                pStmt.setString(2, password);
                rs = pStmt.executeQuery();
                while (rs.next()) {
                    tempLogin = rs.getString("LOGIN");
                    tempPassword = rs.getString("PASSWORD");
                    balance = rs.getDouble("BALANCE");
                    discount = rs.getInt("DISCOUNT");
                    client = new Client();
                    client.setLogin(tempLogin);
                    client.setPassword(tempPassword);
                    client.setBalance(balance);
                    client.setDiscount(discount);
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
        }

        return client;
    }

    @Override
    public Client register(String login, String password) {
        Client client = new Client(login, password);
        em.persist(client);
        return client;
    }

    @Override
    public Client updateBalance(String login, double balance) {
//        TypedQuery<Client> q = em.createQuery("SELECT c FROM Client c WHERE c.login = :login", Client.class);
//        q.setParameter("login", login);
//        Client client = q.getSingleResult();
//        client.setBalance(balance);
//        em.merge(client);
//        return client;
        return null;
    }

}
