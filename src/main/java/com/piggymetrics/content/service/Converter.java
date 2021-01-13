package com.piggymetrics.content.service;

import com.piggymetrics.content.client.dto.ContentDto;
import com.piggymetrics.content.dao.model.Account;
import com.piggymetrics.content.dao.model.Content;
import com.piggymetrics.content.dao.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Converter {

    private final AccountRepository accountRepository;

    public ContentDto convertToContentDTO(Content content) {
        ContentDto contentDTO = new ContentDto();
        contentDTO.setId(content.getId());
        contentDTO.setAccountName(content.getAccount().getName());
        contentDTO.setUrl(content.getUrl());
        contentDTO.setType(content.getType());
        return contentDTO;
    }

    public Content convertToContent(ContentDto contentDTO) {
        String accountName = contentDTO.getAccountName();
        Optional<Account> account = accountRepository.findById(accountName);
        if (account.isPresent()) {
            Content content = new Content();
            content.setId(contentDTO.getId());
            content.setAccount(account.get());
            content.setUrl(contentDTO.getUrl());
            content.setType(contentDTO.getType());
            return content;
        } else {
            throw new EntityNotFoundException("Account not found by name: " + accountName);
        }
    }
}
