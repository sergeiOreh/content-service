package com.piggymetrics.content;

import com.piggymetrics.content.config.PostgresContainer;
import com.piggymetrics.content.config.RabbitContainer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = {PostgresContainer.class, RabbitContainer.class})
public abstract class ContentServiceAbstractTest {
    
}
