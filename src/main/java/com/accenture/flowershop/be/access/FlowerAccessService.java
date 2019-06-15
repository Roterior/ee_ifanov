package com.accenture.flowershop.be.access;

import com.accenture.flowershop.be.entity.Flower;
import java.util.List;

public interface FlowerAccessService {
    
    Flower get(String name);

    List<Flower> getByNameAndPrice(String name, double from, double to);

    Flower update(Flower flower);

}
