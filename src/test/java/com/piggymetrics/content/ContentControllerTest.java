package com.piggymetrics.content;

import com.piggymetrics.content.client.dto.ContentDto;
import com.piggymetrics.content.dao.model.ContentType;
import com.piggymetrics.content.service.ContentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class ContentControllerTest extends ContentServiceAbstractTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ContentService service;

    @Test
    public void getAllTest() throws Exception {
        ContentDto contentDto = new ContentDto();
        contentDto.setAccountName("mark1488");
        contentDto.setType(ContentType.VIDEO);
        List<ContentDto> dtoList = Collections.singletonList(contentDto);

        given(service.getAll()).willReturn(dtoList);

        mvc.perform(get("/")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].accountName", is(contentDto.getAccountName())));
    }

}
