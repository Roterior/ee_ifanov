package com.accenture.flowershop.be.access;

import com.accenture.flowershop.be.entity.Purchase;
import java.util.List;

public interface PurchaseAccessService {

    Purchase add(Purchase purchase);

    List<Purchase> getAll();

    List<Purchase> get(String login);

    Purchase getByIdAndLogin(int id, String login);

    Purchase update(Purchase purchase);

}
