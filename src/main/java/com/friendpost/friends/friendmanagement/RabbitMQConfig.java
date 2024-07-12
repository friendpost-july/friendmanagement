package com.friendpost.friends.friendmanagement;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange("my-fanout-exchange");
    }

    @Bean
    public Queue queue1() {
        return new Queue("my-queue1");
    }

    @Bean
    public Binding binding1(Queue queue1, FanoutExchange exchange) {
        return BindingBuilder.bind(queue1).to(exchange);
    }
}
