package com.accenture.flowershop.fe.dto;

public class BasketItem {

    private String name;
    private double price;
    private int quantity;
    private int getQuantitytobuy;
    private double sum;

    public BasketItem(){}

    public BasketItem(String name, double price, int quantity, int getQuantitytobuy, double sum) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.getQuantitytobuy = getQuantitytobuy;
        this.sum = sum;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getGetQuantitytobuy() {
        return getQuantitytobuy;
    }

    public void setGetQuantitytobuy(int getQuantitytobuy) {
        this.getQuantitytobuy = getQuantitytobuy;
    }
}
