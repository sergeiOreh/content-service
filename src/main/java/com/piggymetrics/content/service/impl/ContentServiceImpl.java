package com.piggymetrics.content.service.impl;

import com.piggymetrics.content.dao.model.Content;
import com.piggymetrics.content.dao.repository.ContentRepository;
import com.piggymetrics.content.service.ContentService;
import org.springframework.stereotype.Service;

@Service
public class ContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;

    public ContentServiceImpl(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @Override
    public Content getByUserId(String name) {
        return contentRepository.findByAccountName(name);
    }

    @Override
    public Content update(String name, Content content) throws Exception {
        Content accountForUpdate = contentRepository.findByAccountName(name);
        if (accountForUpdate != null) {
            accountForUpdate.setUrl(content.getUrl());
            accountForUpdate.setType(content.getType());
            return contentRepository.save(accountForUpdate);
        } else {
            throw new Exception("An account with name '" + name + "' does not exist");
        }
    }
}
