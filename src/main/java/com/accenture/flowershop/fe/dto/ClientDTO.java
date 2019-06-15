package com.accenture.flowershop.fe.dto;

import com.accenture.flowershop.be.entity.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientDTO {

    private Client client;
    private List<BasketItem> basketItemList = new ArrayList<>();

    public ClientDTO() {}

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public List<BasketItem> getBasketItemList() { return basketItemList; }
    public void setBasketItemList(List<BasketItem> basketItemList) { this.basketItemList = basketItemList; }
}
