package com.piggymetrics.content.service;


import com.piggymetrics.content.ContentServiceAbstractTest;
import com.piggymetrics.content.client.dto.ContentDto;
import com.piggymetrics.content.dao.model.Account;
import com.piggymetrics.content.dao.model.Content;
import com.piggymetrics.content.dao.model.ContentType;
import com.piggymetrics.content.dao.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Slf4j
public class ConverterTest extends ContentServiceAbstractTest {

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private Converter converter;

    private static ContentDto contentDto;
    private static Content content;

    @BeforeAll
    public static void createContentDto() throws MalformedURLException {
        contentDto = new ContentDto();
        contentDto.setId(1L);
        contentDto.setAccountName("mark1488");
        contentDto.setType(ContentType.VIDEO);
        contentDto.setUrl(new URL("https://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application"));

        content = new Content();
        content.setId(1L);
        content.setType(ContentType.TEXT);
        content.setUrl(new URL("https://www.wikipedia.com"));
        content.setAccount(new Account("mark1488"));
    }

    @Test
    public void whenConvertDtoToEntity_thenCorrect() {
        when(accountRepository.findById(anyString())).thenReturn(Optional.of(new Account("mark1488")));

        Content content = converter.convertToContent(contentDto);
        log.info(content.toString());
        assertEquals(contentDto.getId(), content.getId());
        assertEquals(contentDto.getAccountName(), content.getAccount().getName());
    }

    @Test
    public void whenConvertEntityToDto_thenCorrect() throws MalformedURLException {
        ContentDto contentDto = converter.convertToContentDTO(content);
        log.info(contentDto.toString());
        assertEquals(content.getId(), contentDto.getId());
        assertEquals(content.getAccount().getName(), contentDto.getAccountName());
    }

    @Test
    public void whenAccountByNameNotFound_thenExceptionThrown() {
        when(accountRepository.findById(anyString())).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            converter.convertToContent(contentDto);
        });

        String expectedMessage = "Account not found by name:";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
