package com.accenture.flowershop.be.business;

import com.accenture.flowershop.be.access.FlowerAccessService;
import com.accenture.flowershop.be.entity.Flower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FlowerServiceImpl implements FlowerService {

    @Autowired
    public FlowerServiceImpl(FlowerAccessService flowerAccessService) {
        this.flowerAccessService = flowerAccessService;
    }

    private final FlowerAccessService flowerAccessService;

    @Override
    public Flower getByName(String name) {
        Flower flower = null;
        if (name != null) {
            try {
                flower = flowerAccessService.get(name);
            } catch (Exception e) {
                return null;
            }
        }
        return flower;
    }

    @Override
    public List<Flower> getByNameAndPrice(String name, double from, double to) {
        return flowerAccessService.getByNameAndPrice(name, from, to);
    }

    @Override
    public Flower updateQuantity(String name, int quantity) {
        Flower flower = flowerAccessService.get(name);
        flower.setQuantity(quantity);
        return flowerAccessService.update(flower);
    }
}
