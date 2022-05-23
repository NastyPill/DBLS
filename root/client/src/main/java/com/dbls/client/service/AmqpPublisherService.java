package com.dbls.client.service;

import com.dbls.client.model.AmqpRequest;
import com.dbls.client.model.AmqpResponse;
import com.dbls.client.service.configuration.RabbitMQConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.TimeoutException;

public class AmqpPublisherService {

    private static Logger LOG = LoggerFactory.getLogger(AmqpPublisherService.class);

    private ObjectMapper objectMapper;
    private RabbitMQConfiguration configuration;
    private Channel channel;

    public AmqpPublisherService(RabbitMQConfiguration configuration) throws IOException, TimeoutException {
        this.configuration = configuration;
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setPort(configuration.getPort());
        connectionFactory.setHost(configuration.getHost());
        channel = connectionFactory.newConnection().createChannel();
        channel.queueDeclare(configuration.getPublishingQueue(), false, false, false, null);
        objectMapper = new ObjectMapper();
    }

    public void publish(AmqpResponse response) throws IOException {
        channel.basicPublish("", configuration.getPublishingQueue(), null, objectMapper.writeValueAsString(response).getBytes());
    }

    public void publish(AmqpRequest request) throws IOException {
        channel.basicPublish("", configuration.getPublishingQueue(), null, objectMapper.writeValueAsString(request).getBytes());
    }
}
