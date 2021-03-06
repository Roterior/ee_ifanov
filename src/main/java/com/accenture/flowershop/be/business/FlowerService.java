package com.accenture.flowershop.be.business;

import com.accenture.flowershop.be.entity.Flower;
import com.accenture.flowershop.be.entity.FlowerFilter;
import java.util.List;

public interface FlowerService {

    Flower getById(Long id);

    List<Flower> getByFilter(FlowerFilter flowerFilter);

    Flower updateQuantity(Long id, Integer quantity);

}
