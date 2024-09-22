package com.example.transaction.domain.port.repository;


import com.example.transaction.domain.model.entity.Supply;

public interface SupplyRepository {
    Supply create (Supply request);
    Supply update (Supply request);
}
