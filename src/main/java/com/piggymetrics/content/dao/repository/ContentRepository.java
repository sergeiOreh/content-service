package com.piggymetrics.content.dao.repository;

import com.piggymetrics.content.dao.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {

    Content findByAccountName(String accountName);
    
}
