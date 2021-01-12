package com.piggymetrics.content.service.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.piggymetrics.content.client.AccountServiceClient;
import com.piggymetrics.content.config.RabbitProperties;
import com.piggymetrics.content.dao.model.Account;
import com.piggymetrics.content.dao.repository.AccountRepository;
import com.rabbitmq.client.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
public class RabbitConsumer {

    private static final String EXCHANGE_NAME = "account_exchange";
    private static final String ACCOUNT_CREATE_ROUTING_KEY = "create";
    private final ConnectionFactory connectionFactory = new ConnectionFactory();

    private final AccountServiceClient accountServiceClient;
    private final AccountRepository accountRepository;
    private final RabbitProperties rabbitProperties;

    @PostConstruct
    private void init() {
        connectionFactory.setHost(rabbitProperties.getHost());
        connectionFactory.setPort(rabbitProperties.getPort());
    }

    @EventListener(ContextRefreshedEvent.class)
    public void consume() throws Exception {
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, ACCOUNT_CREATE_ROUTING_KEY);

        DeliverCallback deliverCallback = getDeliverCallback();
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }

    private DeliverCallback getDeliverCallback() {
        return (consumerTag, delivery) -> {
            String jsonMessage = new String(delivery.getBody(), StandardCharsets.UTF_8);
            log.info(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + jsonMessage + "'");

            Message message = new ObjectMapper().readValue(jsonMessage, Message.class);

            Account account = accountServiceClient.getAccountByName(message.getUserName());

            Account saved = accountRepository.save(account);
            log.info("Account with name '" + saved.getName() + "' saved");
        };
    }
}
