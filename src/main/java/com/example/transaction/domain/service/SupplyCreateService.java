package com.example.transaction.domain.service;
import com.example.transaction.domain.model.dto.command.MessageSupply;
import com.example.transaction.domain.model.dto.command.SupplyCreateCommand;
import com.example.transaction.domain.model.entity.Supply;
import com.example.transaction.domain.model.exception.SupplyException;
import com.example.transaction.domain.port.dao.SupplyDao;
import com.example.transaction.domain.port.publisher.SupplyPublisher;
import com.example.transaction.domain.port.repository.SupplyRepository;

import lombok.AllArgsConstructor;



@AllArgsConstructor
public class SupplyCreateService {
    private final SupplyRepository supplyRepository;
    private final SupplyPublisher supplyPublisher;
    private final SupplyDao supplyDao;

    private static final String MESSAGE_ERROR_ADD = "Supply Exist";

    public Supply execute(SupplyCreateCommand createCommand) {
        validateParams(createCommand);
        Supply supplyToCreate = new Supply().requestToCreate(createCommand);

        MessageSupply messageSupply = new MessageSupply(
                createCommand.getIdArticle(),
                createCommand.getQuantity(),
                createCommand.getPrice()
        );

        supplyPublisher.publishMessage(messageSupply);

        return supplyRepository.create(supplyToCreate);
    }


    private void validateParams(SupplyCreateCommand createCommand) {
        if (createCommand.getId() != null && supplyDao.idExist(createCommand.getId()))
            throw new SupplyException(MESSAGE_ERROR_ADD);

    }
}