package com.accenture.flowershop.be.business;

import com.accenture.flowershop.be.entity.Purchase;
import java.sql.Date;
import java.util.List;

public interface PurchaseService {

    Purchase add(Purchase purchase);

    List<Purchase> getAll();

    Purchase getByIdAndLogin(int id, String login);

    List<Purchase> getByLogin(String login);

    Purchase updateStatus(int id, String login, Date closeDate, String status);

    Purchase updateStatusClose(int id, String login, String status);
}
