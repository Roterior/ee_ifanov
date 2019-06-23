package com.accenture.flowershop.fe.dto;

public class BasketItemDTO {

    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
    private Integer quantityToBuy;
    private Double sum;

    public BasketItemDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantityToBuy() {
        return quantityToBuy;
    }

    public void setQuantityToBuy(Integer quantityToBuy) {
        this.quantityToBuy = quantityToBuy;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }
}
