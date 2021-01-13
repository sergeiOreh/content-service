package com.piggymetrics.content.controller;

import com.piggymetrics.content.client.dto.ContentDto;
import com.piggymetrics.content.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ContentController {

    private final ContentService contentService;

    @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ContentDto> getByAccountName(@PathVariable String name) {
        return contentService.getByAccountName(name);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ContentDto> getAll() {
        return contentService.getAll();
    }

    @PutMapping(value = "/{accountName}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ContentDto> updateByAccountName(@PathVariable String accountName,
                                                @RequestBody List<@Valid ContentDto> contentDTOList) {
        return contentService.updateByAccountName(accountName, contentDTOList);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ContentDto update(@PathVariable Long id,
                             @RequestBody @Valid ContentDto contentDTO) {
        return contentService.update(id, contentDTO);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ContentDto create(@RequestBody @Valid ContentDto contentDTO) {
        return contentService.addNewContent(contentDTO);
    }

    @DeleteMapping(value = "/{id}")
    void deleteById(@PathVariable Long id) {
        contentService.deleteById(id);
    }
}
