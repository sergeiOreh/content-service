package com.piggymetrics.content.client;

import com.piggymetrics.content.dao.model.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

@FeignClient(name = "account-service")
public interface AccountServiceClient {

    @GetMapping("/accounts/test/{name}")
    Account getAccountByName(@PathVariable(value = "name") String name);

    @GetMapping("/accounts")
    List<Account> getAccounts();

    @GetMapping(path = "/accounts/last/{dateCreated}")
    List<Account> getAccountsAfterDate(@PathVariable(value = "dateCreated") Date dateCreated);
}
