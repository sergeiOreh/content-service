package com.piggymetrics.content.service.handler;

import com.piggymetrics.content.client.AccountServiceClient;
import com.piggymetrics.content.dao.model.Account;
import com.piggymetrics.content.dao.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class Scheduler {

    private final AccountServiceClient accountServiceClient;
    private final AccountRepository accountRepository;

    private Date dateOfLastAccount;
    private boolean isFirstCall = true;

    @Scheduled(fixedRate = 5000)
    public void cronJobSch() {
        if (isFirstCall){
            List<Account> accounts = accountServiceClient.getAccounts();
            if (!accounts.isEmpty()) {
                accounts.forEach(accountRepository::save);
                dateOfLastAccount = getDateOfLastAccount(accounts);
            }
            isFirstCall = false;
        }
        List<Account> accountsAfterDate = accountServiceClient.getAccountsAfterDate(dateOfLastAccount);
        if (!accountsAfterDate.isEmpty()) {
            Date lastDate = getDateOfLastAccount(accountsAfterDate);
            if (lastDate.after(dateOfLastAccount)) {
                accountsAfterDate.forEach(accountRepository::save);
                dateOfLastAccount = lastDate;
            }
        }
        log.info("dateOfLastAccount = " + dateOfLastAccount);
    }

    private Date getDateOfLastAccount(List<Account> accounts) {
        return accounts.stream()
                .max(Comparator.comparing(Account::getDateCreated))
                .get()
                .getDateCreated();
    }
}
