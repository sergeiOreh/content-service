package com.piggymetrics.content.config;

import com.piggymetrics.content.client.AccountServiceClient;
import com.piggymetrics.content.dao.repository.AccountRepository;
import com.piggymetrics.content.service.handler.RabbitConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Profile("event")
@RequiredArgsConstructor
@Configuration
public class RabbitContentHandler {

    private final AccountServiceClient accountServiceClient;
    private final AccountRepository accountRepository;
    private final RabbitProperties rabbitProperties;

    @Bean
    public RabbitConsumer rabbitMessageConsumer() {
        log.info("Event profile initializing...");
        return new RabbitConsumer(accountServiceClient, accountRepository, rabbitProperties);
    }

}
