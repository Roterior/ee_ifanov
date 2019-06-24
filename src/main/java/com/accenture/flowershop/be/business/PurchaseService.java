package com.accenture.flowershop.be.business;

import com.accenture.flowershop.be.entity.Purchase;
import java.util.List;

public interface PurchaseService {

    Purchase add(Purchase purchase);

    List<Purchase> getAll();

    Purchase getById(Long id);

    List<Purchase> getByLogin(String login);

    Purchase updateCloseDateAndStatus(Long id);

    Purchase updateStatus(Long id, String status);
}
