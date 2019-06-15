package com.accenture.flowershop.be.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientLogin", insertable = false, updatable = false)
    private Client client;

//    @OneToMany(fetch = FetchType.LAZY)
//    private List<Flower> flowerList = new ArrayList<>();

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
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
//    public List<Flower> getFlowerList() { return flowerList; }
//    public void setFlowerList(List<Flower> flowerList) { this.flowerList = flowerList; }
}
