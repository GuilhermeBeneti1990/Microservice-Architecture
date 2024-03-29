package com.beneti.productapi.modules.sales.rabbitmq;

import com.beneti.productapi.modules.sales.dtos.SalesConfirmationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SalesConfirmationSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${api-config.rabbit.exchange.product")
    private String productTopicExchange;

    @Value("${api-config.rabbit.routingKey.sales-confirmation")
    private String salesConfirmationKey;

    public void sendSalesConfirmationMessage(SalesConfirmationDTO message) {
        try {
            // log.info("Sending message: {}", new ObjectMapper().writeValueAsString(salesConfirmationDTO));
            rabbitTemplate.convertAndSend(productTopicExchange, salesConfirmationKey, message);
            // log.info("Message was sent successfully");
        } catch (Exception ex) {
            // log.info("Error while trying to send sales confirmation message: ", ex);
        }
    }

}
