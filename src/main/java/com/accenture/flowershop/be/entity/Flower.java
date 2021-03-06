package com.accenture.flowershop.be.entity;

import javax.persistence.*;

@Entity
public class Flower {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flower_id")
    @SequenceGenerator(name = "flower_id", sequenceName = "FLOWER_ID_SEQ", allocationSize = 1)
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;

    public Flower() {}

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
}
