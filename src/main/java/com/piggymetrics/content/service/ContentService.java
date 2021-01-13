package com.piggymetrics.content.service;

import com.piggymetrics.content.client.dto.ContentDto;

import java.util.List;

public interface ContentService {

    List<ContentDto> getByAccountName(String accountName);

    List<ContentDto> updateByAccountName(String accountName, List<ContentDto> contentDTOList);

    ContentDto update(Long id, ContentDto contentDTO);

    void deleteById(Long id);

    ContentDto addNewContent(ContentDto contentDto);
    
    List<ContentDto> getAll();
}
