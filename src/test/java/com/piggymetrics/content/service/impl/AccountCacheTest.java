package com.piggymetrics.content.service.impl;

import com.piggymetrics.content.ContentServiceAbstractTest;
import com.piggymetrics.content.dao.model.Account;
import com.piggymetrics.content.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class AccountCacheTest extends ContentServiceAbstractTest {

    @Autowired
    AccountService accountService;

    @Test
    public void create() {
        createAndPrint("Mark", "I was born");
        createAndPrint("Ivan", "I was born");
        createAndPrint("Sergei", "Hi");

        log.info("all entries are below:");
        accountService.getAll().forEach(u -> log.info("{}", u.toString()));
    }

    private void createAndPrint(String name, String note) {
        log.info("created user: {}", accountService.createOrReturnCached(name, note));
    }

    @Test
    public void createAndRefresh() {
        Account account1 = accountService.createOrReturnCached(new Account("Sergei", "Hi"));
        log.info("created account1: {}", account1);

        Account account2 = accountService.createOrReturnCached(new Account("Miha", "Hi"));
        log.info("created account2: {}", account2);

        Account account3 = accountService.createAndRefreshCache(new Account("Mark", "Hi"));
        log.info("created account3: {}", account3);

        Account account4 = accountService.createOrReturnCached(new Account("Kostya", "Hi"));
        log.info("created account4: {}", account4);
    }

}
