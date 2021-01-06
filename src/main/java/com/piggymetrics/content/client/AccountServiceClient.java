package com.piggymetrics.content.client;

import com.piggymetrics.content.client.dto.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "account-service")
public interface AccountServiceClient {

    @GetMapping("/accounts/test/{name}")
    Account getAccountById(@PathVariable(name = "name") String name);
}
