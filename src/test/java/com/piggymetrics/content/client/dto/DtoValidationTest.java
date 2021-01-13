package com.piggymetrics.content.client.dto;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class DtoValidationTest {

    private Validator validator;

    @Before
    public void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void whenAccountDtoNameIsBlank_thenOneConstraintViolation() {
        AccountDto accountDto = new AccountDto("");
        Set<ConstraintViolation<AccountDto>> violations = validator.validate(accountDto);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void whenContentDtoFieldsAreNulls_thenThreeConstraintViolation() {
        ContentDto contentDto = new ContentDto();
        Set<ConstraintViolation<ContentDto>> violations = validator.validate(contentDto);
        assertThat(violations.size()).isEqualTo(3);
    }
}
