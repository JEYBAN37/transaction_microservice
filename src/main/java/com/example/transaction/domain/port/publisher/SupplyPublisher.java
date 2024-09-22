package com.example.transaction.domain.port.publisher;

import com.example.transaction.domain.model.dto.command.MessageSupply;



public interface SupplyPublisher {
    void publishMessage( MessageSupply messageSupply);
}
