package com.accenture.bl.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ord")
    @SequenceGenerator(name = "ord", sequenceName = "ORDER_ID_SEQ")
    private int id;
    private String clientLogin;
    private double totalPrice;
    private Date createDate;
    private Date closeDate;
    private String status;

    public Purchase() {}

    public Purchase(int id, String clientLogin, double totalPrice, Date createDate, Date closeDate, String status) {
        this.id = id;
        this.clientLogin = clientLogin;
        this.totalPrice = totalPrice;
        this.createDate = createDate;
        this.closeDate = closeDate;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getClientLogin() { return clientLogin; }
    public void setClientLogin(String clientLogin) { this.clientLogin = clientLogin; }
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public Date getCreateDate() { return createDate; }
    public void setCreateDate(Date createDate) { this.createDate = createDate; }
    public Date getCloseDate() { return closeDate; }
    public void setCloseDate(Date closeDate) { this.closeDate = closeDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
