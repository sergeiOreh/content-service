package com.piggymetrics.content.service;

import com.piggymetrics.content.dao.model.Account;

import java.util.List;

public interface AccountService {

    Account createOrReturnCached(String name, String note);

    Account createOrReturnCached(Account account);

    Account createAndRefreshCache(Account account);

    List<Account> getAll();

}
