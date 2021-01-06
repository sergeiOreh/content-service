package com.piggymetrics.content.service.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.piggymetrics.content.client.AccountServiceClient;
import com.piggymetrics.content.client.dto.Account;
import com.piggymetrics.content.dao.model.Content;
import com.piggymetrics.content.dao.repository.ContentRepository;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;


@Component
public class MessageConsumer {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String EXCHANGE_NAME = "account_exchange";
    private static final String ACCOUNT_CREATE_ROUTING_KEY = "create";
    private final ConnectionFactory connectionFactory = new ConnectionFactory();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private String port;

    private final AccountServiceClient accountServiceClient;
    private final ContentRepository contentRepository;

    public MessageConsumer(AccountServiceClient accountServiceClient1, ContentRepository contentRepository){
        this.accountServiceClient = accountServiceClient1;
        this.contentRepository = contentRepository;
    }

    @PostConstruct
    private void init() {
        connectionFactory.setHost(host);
        connectionFactory.setPort(new Integer(port));
    }

    @EventListener(ContextRefreshedEvent.class)
    public void consume() throws Exception {
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, ACCOUNT_CREATE_ROUTING_KEY);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String jsonMessage = new String(delivery.getBody(), StandardCharsets.UTF_8);
            log.info(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + jsonMessage + "'");

            Message message = objectMapper.readValue(jsonMessage, Message.class);

            Account accountById = accountServiceClient.getAccountById(message.getUserId());
            log.info("Account from feign: " + accountById.toString());

            addEmptyContentToAccount(accountById);
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }

    private void addEmptyContentToAccount(Account account){
        Content content  = new Content();
        content.setAccountName(account.getName());
        contentRepository.save(content);
    }
}
