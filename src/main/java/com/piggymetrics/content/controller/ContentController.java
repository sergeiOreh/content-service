package com.piggymetrics.content.controller;

import com.piggymetrics.content.dao.model.Content;
import com.piggymetrics.content.service.ContentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContentController {

    private final ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping("/greeting")
    public String getGreeting() {
        return "Hello, I'm ContentService :)";
    }

    @GetMapping("/{name}")
    public Content getUserContent(@PathVariable String name) {
        return contentService.getByUserId(name);
    }

    @PutMapping(value = "/{name}", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Content updateUserContent(@PathVariable String name,
                                     @RequestBody Content newContent) throws Exception {
        return contentService.update(name, newContent);
    }
}
