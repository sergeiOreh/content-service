package com.piggymetrics.content.service.impl;

import com.piggymetrics.content.config.PostgresContainer;
import com.piggymetrics.content.config.RabbitContainer;
import com.piggymetrics.content.dao.model.Content;
import com.piggymetrics.content.dao.repository.ContentRepository;
import com.piggymetrics.content.service.ContentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ContextConfiguration(initializers = {PostgresContainer.class, RabbitContainer.class})
class ContentServiceImplTest {

    @MockBean
    private ContentRepository contentRepository;

    @Autowired
    private ContentService contentService;

    @Test
    void whenUserNotFoundByName_thenExceptionThrows() {
        assertThrows(Exception.class, () -> {
            contentService.update("", new Content());
        });
    }

    @Test
    public void whenValidName_thenContentShouldBeFound() {
        String accountName = "test";
        Content content = new Content();
        content.setAccountName(accountName);

        Mockito.when(contentRepository.findByAccountName(content.getAccountName()))
                .thenReturn(content);

        Content found = contentService.getByUserId(accountName);

        assertEquals(found.getAccountName(), accountName);
    }
}