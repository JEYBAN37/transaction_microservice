package com.example.transaction.application.mapper;

import com.example.transaction.domain.model.dto.SupplyDto;
import com.example.transaction.domain.model.entity.Supply;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface SupplyDtoMapper {
    @Mapping(source = "idArticle", target = "idArticle")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "date", target = "date")
    SupplyDto toDto (Supply objectOfDomain);
}
