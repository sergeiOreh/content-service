package com.piggymetrics.content.service.impl;

import com.piggymetrics.content.dao.model.Account;
import com.piggymetrics.content.dao.repository.AccountRepository;
import com.piggymetrics.content.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Cacheable(value = "accountByNameAndNote", key = "#note")
    @Override
    public Account createOrReturnCached(String name, String note) {
        log.info("creating Account with parameters: {}, {}", name, note);
        return accountRepository.save(new Account(name, note));
    }

    @Override
    @Cacheable(value = "account", key = "#account.note")
    public Account createOrReturnCached(Account account) {
        log.info("creating account: {}", account);
        return accountRepository.save(account);
    }

    @Override
    @CachePut(value = "account", key = "#account.note")
    public Account createAndRefreshCache(Account account) {
        log.info("creating account: {}", account);
        return accountRepository.save(account);
    }

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }
}
