package com.example.transaction.domain.port.repository;

import com.example.transaction.domain.model.entity.Sale;

import java.util.List;

public interface SaleRepository {
    List<Sale> create(List<Sale> request);
}
