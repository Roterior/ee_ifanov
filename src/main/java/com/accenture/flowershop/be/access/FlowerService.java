package com.accenture.flowershop.be.access;

import com.accenture.flowershop.be.entity.Flower;
import java.util.List;

public interface FlowerService {

    Flower findByName(String name);

    List<Flower> get(String name, double from, double to);

    Flower updateQuantity(String name, int quantity);

}
