package com.accenture.fl;

import com.accenture.bl.entity.Flower;

import java.util.List;

public interface FlowerService {

    Flower findByName(String name);

    List<Flower> findbyPrice(double from, double to);

    List<Flower> get(String name, double from, double to);

    Flower updateQuantity(String name, int quantity);

}
