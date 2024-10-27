package com.example.transaction.application.mapper;

import com.example.transaction.domain.model.dto.SaleDto;
import com.example.transaction.domain.model.entity.Sale;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SaleDtoMapper {
    SaleDto toDto (Sale objectOfDomain);
}
