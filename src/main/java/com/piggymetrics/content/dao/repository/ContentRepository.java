package com.piggymetrics.content.dao.repository;

import com.piggymetrics.content.dao.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContentRepository extends JpaRepository<Content, Long> {

    Optional<List<Content>> findAllByAccountName(String accountName);
    
}
