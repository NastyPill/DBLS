package com.dbls.client.service.configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@Builder
@ToString
@Getter
public class ClientConfiguration {

    private static Logger LOG = LoggerFactory.getLogger(ClientConfiguration.class);

    private RabbitMQConfiguration rabbitMQConfiguration;

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
