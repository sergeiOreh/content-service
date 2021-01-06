package com.piggymetrics.content.config;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.RabbitMQContainer;

public class RabbitContainer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public static final String RABBIT_IMAGE = "rabbitmq:3-management";

    private final RabbitMQContainer rabbitMQContainer = new RabbitMQContainer(RABBIT_IMAGE)
            .withExposedPorts(5672, 15672);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        rabbitMQContainer.start();
        TestPropertyValues.of(
                "spring.rabbitmq.host=" + rabbitMQContainer.getContainerIpAddress(),
                "spring.rabbitmq.port=" + rabbitMQContainer.getMappedPort(5672))
                .applyTo(applicationContext.getEnvironment());

    }
}
