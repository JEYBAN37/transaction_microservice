package com.example.transaction.domain.service;
import com.example.transaction.domain.model.dto.command.MessageSupply;
import com.example.transaction.domain.model.dto.command.SupplyCreateCommand;
import com.example.transaction.domain.model.entity.Supply;
import com.example.transaction.domain.model.exception.SupplyException;
import com.example.transaction.domain.port.dao.SupplyDao;
import com.example.transaction.domain.port.publisher.SupplyPublisher;
import com.example.transaction.domain.port.repository.SupplyRepository;
import lombok.AllArgsConstructor;
import java.util.List;

@AllArgsConstructor
public class SupplyCreateService {
    private final SupplyRepository supplyRepository;
    private final SupplyPublisher supplyPublisher;
    private final SupplyDao supplyDao;

    private static final String MESSAGE_ERROR_ADD = "Supply Exist";

    public List<Supply> execute(List<SupplyCreateCommand> createCommands) {
        if (createCommands.isEmpty())
           throw new SupplyException("List Empty");
        return createCommands.stream()
                .map(this::processCreateCommand)
                .toList();
    }

    private Supply processCreateCommand(SupplyCreateCommand createCommand) {
        validateParams(createCommand);
        Supply supplyToCreate = createSupply(createCommand);
        publishSupplyMessage(createCommand);
        return supplyRepository.create(supplyToCreate);
    }

    private Supply createSupply(SupplyCreateCommand createCommand) {
        return new Supply().requestToCreate(createCommand);
    }

    private void publishSupplyMessage(SupplyCreateCommand createCommand) {
        MessageSupply messageSupply = new MessageSupply(
                createCommand.getIdArticle(),
                createCommand.getQuantity(),
                createCommand.getPrice()
        );
        supplyPublisher.publishMessage(messageSupply);
    }

    private void validateParams(SupplyCreateCommand createCommand) {
        if (createCommand.getId() != null && supplyDao.idExist(createCommand.getId()))
            throw new SupplyException(MESSAGE_ERROR_ADD);

    }
}