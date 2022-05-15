package com.dbls.client.service.configuration;

import lombok.Builder;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@Builder
@ToString
public class ClientConfiguration {

    private static Logger LOG = LoggerFactory.getLogger(ClientConfiguration.class);

    RabbitMQConfiguration rabbitMQConfiguration;

    public static ClientConfiguration readConfiguration(String path) {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        return ClientConfiguration.builder()
                .rabbitMQConfiguration(RabbitMQConfiguration.readConfiguration(properties))
                .build();
    }

}
