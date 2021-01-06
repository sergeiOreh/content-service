package com.piggymetrics.content.service;

import com.piggymetrics.content.dao.model.Content;

public interface ContentService {

    Content getByUserId(String name);
    Content update(String name, Content content) throws Exception;
    
}
