package com.accenture.flowershop.be.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Client {

    @Id
    private String login;
    @Column(nullable = false)
    private String password;
    private String lName;
    private String fName;
    private String mName;
    private String address;
    private String phoneNumber;
    @Column(nullable = false, columnDefinition = "Decimal(10,2) default '2000.00'")
    private double balance = 2000;
    @Column(nullable = false)
    private int discount = 5;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Purchase> purchaseList = new ArrayList<>();

    public Client() {}

    public Client(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Client(String login, String password, String lName, String fName, String mName, String address, String phoneNumber) {
        this.login = login;
        this.password = password;
        this.lName = lName;
        this.fName = fName;
        this.mName = mName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getlName() { return lName; }
    public void setlName(String lName) { this.lName = lName; }
    public String getfName() { return fName; }
    public void setfName(String fName) { this.fName = fName; }
    public String getmName() { return mName; }
    public void setmName(String mName) { this.mName = mName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    public int getDiscount() { return discount; }
    public void setDiscount(int discount) { this.discount = discount; }
    public List<Purchase> getPurchaseList() { return purchaseList; }
    public void setPurchaseList(List<Purchase> purchaseList) { this.purchaseList = purchaseList; }
}
