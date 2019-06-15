package com.accenture.flowershop.be.business;

import com.accenture.flowershop.be.access.PurchaseAccessService;
import com.accenture.flowershop.be.entity.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.*;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    public PurchaseServiceImpl(PurchaseAccessService purchaseAccessService) {
        this.purchaseAccessService = purchaseAccessService;
    }

    private final PurchaseAccessService purchaseAccessService;

    @Override
    public Purchase add(Purchase purchase) {
        return purchaseAccessService.add(purchase);
    }

    @Override
    public List<Purchase> getByLogin(String clientLogin) {
        return purchaseAccessService.get(clientLogin);
    }

    @Override
    public Purchase updateStatus(int id, String clientLogin, Date closeDate) {
        Purchase purchase = purchaseAccessService.getByIdAndLogin(id, clientLogin);
        purchase.setStatus("paid");
        purchase.setCloseDate(closeDate);
        return purchaseAccessService.update(purchase);
    }
}
