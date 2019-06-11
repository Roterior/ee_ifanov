package com.accenture.bl.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Flower {

    @Id
    private String name;
    private double price;
    private int quantity;

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
}
