package com.example.transaction.infrastructure.adapter.mapper;

import com.example.transaction.domain.model.entity.Sale;
import com.example.transaction.domain.model.entity.salesvalidates.State;
import com.example.transaction.infrastructure.adapter.entity.SaleEntity;
import com.example.transaction.infrastructure.adapter.json.SaleJson;
import org.springframework.stereotype.Component;

@Component
public class SaleDboMapper {

    public Sale toDomainJson(SaleJson entity, State state , int quantity) {
        if(entity == null){
            return null;
        }
        return  Sale.jsonSale(
                entity.getId(),
                quantity,
                state,
                entity.getPrice()
              );
    }

    public Sale toDomain (SaleEntity entity){
        if(entity == null){
            return null;
        }
        return new Sale(
                entity.getIdArticle(),
                entity.getIdUser(),
                entity.getQuantity(),
                entity.getState(),
                entity.getPrice()
        );
    }



    public SaleEntity toDatabase(Sale domain) {
        if (domain == null) {
            return null;
        }
        return new SaleEntity(
                domain.getId(),
                domain.getIdArticle(),
                domain.getIdUser(),
                domain.getQuantity(),
                domain.getState(),
                domain.getPrice(),
                domain.getDate()
        );
    }
}
