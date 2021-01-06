package com.piggymetrics.content.config;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresContainer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public static final String POSTGRES_IMAGE = "postgres:13.1";
    public static final String DATABASE_NAME = "CONTENT_DATABASE";

    private final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer<>(POSTGRES_IMAGE)
            .withReuse(true)
            .withDatabaseName(DATABASE_NAME)
            .withUsername("postgres")
            .withPassword("password");


    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

        postgreSQLContainer.start();
        TestPropertyValues.of("spring.datasource.url=" +
                postgreSQLContainer.getJdbcUrl()).applyTo(applicationContext.getEnvironment());
    }
}
