package com.piggymetrics.content.service.impl;

import com.piggymetrics.content.client.dto.ContentDto;
import com.piggymetrics.content.dao.model.Account;
import com.piggymetrics.content.dao.model.Content;
import com.piggymetrics.content.dao.repository.AccountRepository;
import com.piggymetrics.content.dao.repository.ContentRepository;
import com.piggymetrics.content.service.ContentService;
import com.piggymetrics.content.service.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;
    private final AccountRepository accountRepository;
    private final Converter converter;

    @Cacheable(value = "contentsByAccountName", key = "#accountName")
    @Override
    public List<ContentDto> getByAccountName(String accountName) {
        log.info("getting content by accountName: {}", accountName);
        List<Content> contentList = contentRepository.findAllByAccountName(accountName)
                .orElseThrow(() -> new EntityNotFoundException("Content not found by name " + accountName));

        return contentList.stream().map(converter::convertToContentDTO).collect(Collectors.toList());
    }

    @CachePut(value = "contentsByAccountName", key = "#accountName")
    @Override
    public List<ContentDto> updateByAccountName(String accountName, List<ContentDto> newContents) {
        log.info("updating content by accountName: {}", accountName);
        return accountRepository.findById(accountName)
                .map(account -> {
                    updateContentForAccount(newContents, account);

                    return accountRepository.save(account)
                            .getContentList()
                            .stream()
                            .map(converter::convertToContentDTO)
                            .collect(Collectors.toList());
                })
                .orElseThrow(() -> new EntityNotFoundException("Account not found by name " + accountName));
    }

    private void updateContentForAccount(List<ContentDto> newContentDtos, Account account) {
        Map<Long, Content> currentContent = account.getContentList()
                .stream()
                .collect(Collectors.toMap(
                        Content::getId,
                        Function.identity()
                ));

        newContentDtos.forEach(newContentDto -> {
            if (currentContent.containsKey(newContentDto.getId())) {
                Content contentToUpdate = currentContent.get(newContentDto.getId());
                contentToUpdate.setUrl(newContentDto.getUrl());
                contentToUpdate.setType(newContentDto.getType());
            } else {
                throw new EntityNotFoundException("Content not found by id");
            }
        });
    }

    @Override
    public ContentDto update(Long id, ContentDto contentDTO) {
        Content newContent = converter.convertToContent(contentDTO);

        return contentRepository.findById(id)
                .map(content -> {
                    content.setUrl(newContent.getUrl());
                    content.setType(newContent.getType());
                    content.setAccount(newContent.getAccount());
                    return converter.convertToContentDTO(contentRepository.save(content));
                })
                .orElseGet(() -> {
                    newContent.setId(id);
                    return converter.convertToContentDTO(contentRepository.save(newContent));
                });
    }

    @Override
    public void deleteById(Long id) {
        contentRepository.deleteById(id);
    }

    @Override
    public ContentDto addNewContent(ContentDto contentDto) {
        Content savedContent = contentRepository.save(converter.convertToContent(contentDto));
        return converter.convertToContentDTO(savedContent);
    }
}
