package com.example.transaction.infrastructure.adapter.mqtt;


import com.example.transaction.domain.model.dto.command.MessageSupply;
import com.example.transaction.domain.port.publisher.SupplyPublisher;

import com.example.transaction.infrastructure.beanconfiguration.SupplyBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Service
public class MqttSupplyPublisher implements SupplyPublisher {


    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MqttSupplyPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishMessage( MessageSupply messageSupply) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMessage = objectMapper.writeValueAsString(messageSupply);
            rabbitTemplate.convertAndSend(SupplyBean.EXCHANGE_NAME, "routing.key", jsonMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
