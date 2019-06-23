package com.accenture.flowershop.be.business;

import com.accenture.flowershop.be.access.PurchaseDAO;
import com.accenture.flowershop.be.entity.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.*;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseDAO purchaseDAO;

    @Autowired
    public PurchaseServiceImpl(PurchaseDAO purchaseDAO) {
        this.purchaseDAO = purchaseDAO;
    }

    @Override
    public Purchase add(Purchase purchase) {
        return purchaseDAO.add(purchase);
    }

    @Override
    public List<Purchase> getAll() {
        try {
            return purchaseDAO.getAll();
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public Purchase getById(Long id) {
        try {
            return purchaseDAO.getById(id);
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Purchase> getByLogin(String clientLogin) {
        try {
            return purchaseDAO.getByLogin(clientLogin);
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public Purchase updateCloseDateAndStatus(Long id, String status) {
        try {
            Purchase purchase = purchaseDAO.getById(id);
            purchase.setStatus(status);
            purchase.setCloseDate(new Date(System.currentTimeMillis()));
            return purchaseDAO.update(purchase);
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public Purchase updateStatus(Long id, String status) {
        try {
            Purchase purchase = purchaseDAO.getById(id);
            purchase.setStatus(status);
            return purchaseDAO.update(purchase);
        }
        catch (Exception e) {
            return null;
        }
    }
}
