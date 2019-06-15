package com.accenture.flowershop.be.entity;

import javax.persistence.*;

@Entity
public class Flower {

    @Id
    private String name;
    private double price;
    private int quantity;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "name", insertable = false, updatable = false)
//    private Purchase purchase;

    public Flower() {}

    public Flower(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
//    public Purchase getPurchase() { return purchase; }
//    public void setPurchase(Purchase purchase) { this.purchase = purchase; }
}
