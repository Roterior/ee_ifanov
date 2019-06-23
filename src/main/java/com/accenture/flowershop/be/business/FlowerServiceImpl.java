package com.accenture.flowershop.be.business;

import com.accenture.flowershop.be.access.FlowerDAO;
import com.accenture.flowershop.be.entity.Flower;
import com.accenture.flowershop.be.entity.FlowerFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FlowerServiceImpl implements FlowerService {

    private final FlowerDAO flowerDAO;

    @Autowired
    public FlowerServiceImpl(FlowerDAO flowerDAO) {
        this.flowerDAO = flowerDAO;
    }

    @Override
    public Flower getById(Long id) {
        if (id != null) {
            try {
                return flowerDAO.getById(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<Flower> getByFilter(FlowerFilter flowerFilter) {
        try {
            return flowerDAO.getByFilter(flowerFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Flower updateQuantity(Long id, Integer quantity) {
        try {
            Flower flower = flowerDAO.getById(id);
            flower.setQuantity(quantity);
            return flowerDAO.update(flower);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
