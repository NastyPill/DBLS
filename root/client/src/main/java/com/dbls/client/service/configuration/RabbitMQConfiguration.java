package com.dbls.client.service.configuration;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

import java.util.Properties;

@Builder
@Value
public class RabbitMQConfiguration {

    String host;
    Integer port;
    String consumableQueue;
    String publishingQueue;

    public static RabbitMQConfiguration readConfiguration(Properties properties) {
        return RabbitMQConfiguration.builder()
                .host(properties.getProperty("rabbitmq.host"))
                .port(Integer.parseInt(properties.getProperty("rabbitmq.port")))
                .consumableQueue(properties.getProperty("rabbitmq.queue.in"))
                .publishingQueue(properties.getProperty("rabbitmq.queue.out"))
                .build();
    }

}
