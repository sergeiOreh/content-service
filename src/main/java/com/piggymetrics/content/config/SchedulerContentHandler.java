package com.piggymetrics.content.config;

import com.piggymetrics.content.client.AccountServiceClient;
import com.piggymetrics.content.dao.repository.AccountRepository;
import com.piggymetrics.content.service.handler.Scheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@Profile("cron")
@EnableScheduling
@RequiredArgsConstructor
@Configuration
public class SchedulerContentHandler {

    private final AccountServiceClient accountServiceClient;
    private final AccountRepository accountRepository;

    @Bean
    public Scheduler scheduler() {
        log.info("Cron profile initializing...");
        return new Scheduler(accountServiceClient, accountRepository);
    }

}
