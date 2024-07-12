package com.friendpost.friends.friendmanagement.service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String EXCHANGE_NAME = "my-fanout-exchange";

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "", message);
    }
}

