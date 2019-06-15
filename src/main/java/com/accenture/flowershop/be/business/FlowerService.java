package com.accenture.flowershop.be.business;

import com.accenture.flowershop.be.entity.Flower;
import java.util.List;

public interface FlowerService {

    Flower getByName(String name);

    List<Flower> getByNameAndPrice(String name, double from, double to);

    Flower updateQuantity(String name, int quantity);

}
