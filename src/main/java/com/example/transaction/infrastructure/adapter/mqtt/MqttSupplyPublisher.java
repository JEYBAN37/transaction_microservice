package com.example.transaction.infrastructure.adapter.mqtt;
import com.example.transaction.domain.model.dto.command.MessageSupply;
import com.example.transaction.domain.port.publisher.SupplyPublisher;
import com.example.transaction.infrastructure.beanconfiguration.SupplyBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class MqttSupplyPublisher implements SupplyPublisher {


    private final RabbitTemplate rabbitTemplate;

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
