package com.accenture.flowershop.be.access;

import com.accenture.flowershop.be.entity.Purchase;
import java.sql.Date;
import java.util.List;

public interface PurchaseService {

    Purchase add(String login, Date createDate, double price, String status);

    List<Purchase> get(String login);

    Purchase updateStatus(int id, String login, Date closeDate);

}
