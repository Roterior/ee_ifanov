package com.accenture.bl.entity;

public class Client {

    private String login;
    private String password;
    private String lName;
    private String fName;
    private String mName;
    private String address;
    private int phoneNumber;
    private double balance;
    private int discount;

    public Client() {}

    public Client(String login, String password, String lName, String fName, String mName, String address, int phoneNumber, double balance, int discount) {
        this.login = login;
        this.password = password;
        this.lName = lName;
        this.fName = fName;
        this.mName = mName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
        this.discount = discount;
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
    public int getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(int phoneNumber) { this.phoneNumber = phoneNumber; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    public int getDiscount() { return discount; }
    public void setDiscount(int discount) { this.discount = discount; }
}
