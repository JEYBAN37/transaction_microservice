package com.example.transaction.application.command;

import com.example.transaction.application.mapper.SupplyDtoMapper;
import com.example.transaction.domain.model.dto.SupplyDto;
import com.example.transaction.domain.model.dto.command.SupplyCreateCommand;
import com.example.transaction.domain.service.SupplyCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SupplyCreateHandler {
    private final SupplyCreateService supplyCreateService;
    private final SupplyDtoMapper supplyDtoMapper;


    @Autowired
    public SupplyCreateHandler(SupplyCreateService supplyCreateService, SupplyDtoMapper supplyDtoMapper) {
        this.supplyCreateService = supplyCreateService;
        this.supplyDtoMapper = supplyDtoMapper;
    }

    public List<SupplyDto> execute (List<SupplyCreateCommand> createCommand){
        return supplyCreateService.execute(createCommand).stream()
                .map(supplyDtoMapper::toDto)
                .toList();
    }

}
