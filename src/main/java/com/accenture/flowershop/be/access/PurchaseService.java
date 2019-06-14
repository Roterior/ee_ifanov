package com.accenture.flowershop.be.access;

import com.accenture.flowershop.be.entity.Purchase;
import java.sql.Date;
import java.util.List;

public interface PurchaseService {

    Purchase add(Purchase purchase);

    List<Purchase> get(String login);

    Purchase updateStatus(int id, String login, Date closeDate);

}
