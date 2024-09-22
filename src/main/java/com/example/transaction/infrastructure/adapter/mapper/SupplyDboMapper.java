package com.example.transaction.infrastructure.adapter.mapper;

import com.example.transaction.domain.model.dto.SupplyDto;
import com.example.transaction.domain.model.entity.Supply;
import com.example.transaction.infrastructure.adapter.entity.SupplyEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class SupplyDboMapper {

public SupplyEntity toDatabase (Supply domain){
    if(domain == null){
        return null;
    }
    return new SupplyEntity(
            domain.getId(),
            domain.getIdArticle(),
            domain.getQuantity(),
            domain.getState(),
            domain.getPrice(),
            domain.getDate()
    );
}

    public Supply toDomain(SupplyEntity entity){
        if(entity == null){
            return null;
        }
        return new Supply(
                entity.getId(),
                entity.getIdArticle(),
                entity.getQuantity(),
                entity.getState(),
                entity.getPrice()
                );
    }

    public SupplyEntity toDto (SupplyDto supplyDto) {
        if(supplyDto == null){
            return null;
        }
        return new SupplyEntity(
                supplyDto.getId(),
                supplyDto.getIdArticle(),
                supplyDto.getQuantity(),
                supplyDto.getState(),
                supplyDto.getPrice(),
                supplyDto.getDate()
        );
    }

    public SupplyDto toDto (SupplyEntity supplyEntity) {
        if(supplyEntity == null){
            return null;
        }
        return new SupplyDto(
                supplyEntity.getId(),
                supplyEntity.getIdArticle(),
                supplyEntity.getQuantity(),
                supplyEntity.getState(),
                supplyEntity.getPrice(),
                supplyEntity.getDate()
        );
    }


}
