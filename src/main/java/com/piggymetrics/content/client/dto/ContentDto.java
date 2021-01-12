package com.piggymetrics.content.client.dto;

import com.piggymetrics.content.dao.model.ContentType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URL;

@Getter
@Setter
public class ContentDto {

    private Long id;

    @NotBlank
    private String accountName;

    @NotNull
    private ContentType type;

    @NotNull
    private URL url;

}
