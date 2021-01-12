package com.piggymetrics.content.dao.repository;

import com.piggymetrics.content.dao.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
