package com.example.transaction.domain.port.dao;



import com.example.transaction.domain.model.entity.Supply;

import java.sql.Timestamp;


public interface SupplyDao {
    Supply getSupply (Long id);
    boolean dateExist(Timestamp email);
    boolean idExist(Long id);
}
